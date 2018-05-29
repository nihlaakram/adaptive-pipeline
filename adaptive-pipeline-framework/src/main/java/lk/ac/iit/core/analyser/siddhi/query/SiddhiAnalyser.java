/**
 * Copyright 2018, Nihla Akram
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package lk.ac.iit.core.analyser.siddhi.query;

import lk.ac.iit.core.analyser.GreedyOptimizer;
import lk.ac.iit.core.analyser.data.AnalysedData;
import lk.ac.iit.core.analyser.data.AnalyserData;
import lk.ac.iit.core.analyser.siddhi.extension.LatencyAttributeAggregator;
import lk.ac.iit.core.analyser.siddhi.extension.TpsAttributeAggregator;
import lk.ac.iit.core.monitor.Monitor;
import lk.ac.iit.core.planner.Planner;
import lk.ac.iit.core.planner.PlannerData;
import lk.ac.iit.visual.data.logger.ExternalLogger;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

import java.util.ArrayList;
import java.util.List;

public class SiddhiAnalyser {
    private static final Logger log = Logger.getLogger(SiddhiAnalyser.class);
    private static List<AnalyserData> analyserDataList = new ArrayList<AnalyserData>();
    private final InputHandler inputHandler;
    private final String query;
    private final String inStreamDefinition;
    private final SiddhiManager siddhiManager;
    private SiddhiAppRuntime siddhiAppRuntime;
    private Planner planner;
    private boolean isScale;
    private int plannerCount = 0;


    /**
     * Constructor : creates the instance of the Siddhi analyser
     *
     * @param monitorThreshold the number of events to be monitored
     * @param noOfParameters   the no of parameters analysed
     * @param planner          the planning component of the framework
     * @param isScale          should the application scale
     */
    public SiddhiAnalyser(int monitorThreshold, int noOfParameters, Planner planner, boolean isScale) {
        BasicConfigurator.configure(new ConsoleAppender(new PatternLayout("[%p] - %m%n")));
        TpsAttributeAggregator.monitorThreshold = monitorThreshold;
        this.planner = planner;
        this.isScale = isScale;
        String in = "tt long";
        String out = "tt double";
        String autoQuery = "learner:latency(tt) as tt";
        ;
        for (int i = 1; i < noOfParameters; i++) {
            in = in.concat(",tt" + i + " long");
            out = out.concat(",tt" + i + " double");
            if (i < noOfParameters / 2) {
                autoQuery = autoQuery.concat(", learner:latency(tt" + i + ") as tt" + i);
            } else {
                autoQuery = autoQuery.concat(", learner:tps(tt" + i + ") as tt" + i);
            }
        }

        this.inStreamDefinition = "define stream inputStream (" + in + "); " +
                "define stream outputStream (" + out + ");";
        this.query = "@info(name = 'query1') " + "from inputStream#window.lengthBatch(" + monitorThreshold + ") " +
                "select " + autoQuery + " insert into filteredOutputStream";

        this.siddhiManager = new SiddhiManager();
        this.siddhiManager.setExtension("learner:latency", LatencyAttributeAggregator.class);
        this.siddhiManager.setExtension("learner:tps", TpsAttributeAggregator.class);
        this.siddhiAppRuntime = this.siddhiManager.
                createSiddhiAppRuntime(inStreamDefinition + query);
        this.inputHandler = this.siddhiAppRuntime.getInputHandler("inputStream");
        initCallback(noOfParameters);
        this.siddhiAppRuntime.start();


    }

    /**
     * Returns the analysed results from Siddhi
     *
     * @param noOfParameters
     */

    private void initCallback(int noOfParameters) {
        this.siddhiAppRuntime.addCallback("filteredOutputStream", new StreamCallback() {
            @Override
            public void receive(org.wso2.siddhi.core.event.Event[] events) {
                for (Event ev : events) {


                    double[] latency = new double[noOfParameters / 2];
                    double[] tps = new double[noOfParameters / 2];

                    log.info("\n- - - - - - - - - - - - - - - - - - - -" +
                            "\nAnalysis Component Logger, Performance values at :"
                            + System.currentTimeMillis());

                    ExternalLogger.getLogger().log("- - - - - - - - - - - - - - - - - - - -" +
                            "\nAnalysis Component Logger, Performance values at :"
                            + System.currentTimeMillis());
                    for (int i = 0; i < noOfParameters / 2; i++) {
                        latency[i] = (double) ev.getData()[i];
                        tps[i] = (double) ev.getData()[i + (noOfParameters / 2)];
                        log.info("Performance for Stage" + (i + 1) + " -> latency : " + latency[i] + " ms , tps : " +
                                "" + tps[i] + " req/sec");
                        ExternalLogger.getLogger().log("\nPerformance for Stage" + (i + 1) + " -> latency : " + latency[i] + " ms , tps : " +
                                "" + tps[i] + " req/sec");
                    }


                    AnalyserData analyserData = new AnalyserData(tps, latency);

                    GreedyOptimizer optimizer = new GreedyOptimizer(analyserData);

                    AnalysedData analysedData = optimizer.analyse();


                    analyserDataList.add(analyserData);
                    reportPerformance();

                    PlannerData plannerData = planner.plan(analysedData);


                    log.info("- - - - - - - - - - - - - - - - - - - -" +
                            "\nPlaning Component Logger, Scaling analysis and planing summary ");
                    log.info("Scaling status : " + plannerData.isScalability() + "\nScalable Stage : " +
                            plannerData.getStageID());

                    if (plannerCount < 1) {
                        ExternalLogger.getLogger().log("- - - - - - - - - - - - - - - - - - - -" +
                                "\nPlaning Component Logger, Scaling analysis and planing summary " +
                                "\nScaling status : " + plannerData.isScalability() + "\nScalable Stage : " +
                                plannerData.getStageID());

                        if (isScale && plannerData.isScalability()) {
                            Monitor.getMonitor().getExecutor().executeScaling(plannerData.getStageID());

                        }

                    }

                    plannerCount++;


                }
            }
        });
    }

    private void reportPerformance() {
        if (analyserDataList.size() % 2 == 0) {
            int bef = analyserDataList.size() - 2;
            int af = bef + 1;
            int stageCount = analyserDataList.get(af).getAvgLatency().length;

            double befTps = getMinValue(analyserDataList.get(bef).getTpsArr());
            double afTps = getMinValue(analyserDataList.get(af).getTpsArr());

            double befLat = 0;
            double afLat = 0;

            for (int i = 0; i < stageCount; i++) {

                befLat += analyserDataList.get(bef).getAvgLatency()[i];
                afLat += analyserDataList.get(af).getAvgLatency()[i];
            }

            double befRat = (befTps / befLat);
            double afRat = (afTps / afLat);

            double tpsPerc = ((afTps - befTps) / befTps) * 100;
            double latPerc = ((befLat - afLat) / befLat) * 100;
            double ratPerc = ((afRat - befRat) / befRat) * 100;

            log.info("Effect on TPS : " + tpsPerc + "%");
            log.info("Effect on Latency : " + latPerc + "%");
            log.info("Effect on overall performance : " + ratPerc + "%");


            ExternalLogger.getLogger().log("\nEffect on TPS : " + tpsPerc + "%");
            ExternalLogger.getLogger().log("\nEffect on Latency : " + latPerc + "%");
            ExternalLogger.getLogger().log("\nEffect on overall performance : " + ratPerc + "%");
            ExternalLogger.getLogger().close();
        } else {

        }
    }

    private double getMinValue(double[] valueArr) {
        double min = valueArr[0];
        for (int i = 1; i < valueArr.length; i++) {
            if (valueArr[i] < min) {
                min = valueArr[i];
            }
        }
        return min;
    }


    /**
     * Publishes events to Siddhi
     *
     * @param event The event to be published
     */
    public void publish(Event event) {
        try {
            inputHandler.send(event);
        } catch (InterruptedException e) {

        }

    }


}

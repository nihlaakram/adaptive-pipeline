package lk.ac.iit.core.analyser.learner.query;

import lk.ac.iit.core.Monitor;
import lk.ac.iit.core.analyser.AnalyserData;
import lk.ac.iit.core.analyser.learner.extension.LatencyAttributeAggregator;
import lk.ac.iit.core.analyser.learner.extension.TpsAttributeAggregator;
import lk.ac.iit.core.planner.Planner;
import lk.ac.iit.core.planner.PlannerData;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

public class SiddhiLearner {
    private final InputHandler inputHandler;
    private final String query;
    private final String inStreamDefinition;
    private final SiddhiManager siddhiManager;
    private SiddhiAppRuntime siddhiAppRuntime;


    public SiddhiLearner(int monitorThreshold, int noOfParameters) {
        TpsAttributeAggregator.monitorThreshold = monitorThreshold;

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

    private void initCallback(int noOfParameters) {
        this.siddhiAppRuntime.addCallback("filteredOutputStream", new StreamCallback() {
            @Override
            public void receive(org.wso2.siddhi.core.event.Event[] events) {
                for (Event ev : events) {
                    System.out.println(ev);//(double) ev.getData()[0], (double) ev.getData()[2]

                    double[] latency = new double[noOfParameters / 2];
                    double[] tps = new double[noOfParameters / 2];

                    //preprocessed attributes for latency and tps
                    for (int i = 0; i < noOfParameters / 2; i++) {
                        latency[i] = (double) ev.getData()[i];
                        tps[i] = (double) ev.getData()[i + (noOfParameters / 2)];
                    }

                    Planner planner = new Planner(new AnalyserData(tps, latency), 10);
                    PlannerData plannerData = planner.plan();
                    System.out.println(plannerData.isScalability() + "\t" + plannerData.getStageID());

                    if (plannerData.isScalability()) {
                        Monitor.getMonitor().getExecutor().executeScaling(plannerData.getStageID());

                    }


                }
            }
        });
    }


    public void publish(Event obj) {
        try {
            inputHandler.send(obj);
        } catch (InterruptedException e) {

        }

    }


}

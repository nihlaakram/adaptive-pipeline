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

package lk.ac.iit.handler;

import lk.ac.iit.core.analyser.data.AnalyserReport;
import lk.ac.iit.data.PipeData;
import lk.ac.iit.data.WorkLoadData;
import lk.ac.iit.usecase.builder.handler.XMLMessage;
import lk.ac.iit.visual.data.logger.PerfLogger;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PerformanceHandler implements Runnable {


    private static final Logger log = Logger.getLogger(PerformanceHandler.class);
    private BlockingQueue<PipeData> inQueue;
    private int messageCount;
    private long startTime;
    private PerfLogger logger = PerfLogger.getLogger();
    private List<AnalyserReport> performanceReport = new ArrayList<>();


    /**
     * Constructor
     *
     * @param inQueue   the input inQueue
     * @param startTime the start time of the application
     */
    public PerformanceHandler(LinkedBlockingQueue<PipeData> inQueue, long startTime) {
        this.inQueue = inQueue;
        this.startTime = startTime;
    }

    public void run() {

        long totalLatency = 0;
        while (true) {
            if (this.inQueue.size() > 0) {
                XMLMessage msg = null;
                try {
                    msg = (XMLMessage) this.inQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (msg.getTimestamp() == WorkLoadData.scale()) {

                    calculatePerformance(totalLatency);
//                    totalLatency = 0;
                } else if (msg.getTimestamp() == WorkLoadData.termination()) {
                    calculatePerformance(totalLatency);
                    //display
                    //Application.launch(MainFX.class);
                    break;
                } else {
                    this.messageCount++;
                    totalLatency += (System.currentTimeMillis() - msg.getTimestamp());

                }


            }


        }

    }

    /**
     * Calculates average latency and throughput
     *
     * @param totalLatency the cumulative latency of the application
     */
    private void calculatePerformance(long totalLatency) {
        long endTime = System.currentTimeMillis();
        double latency = totalLatency / this.messageCount;
        double runTime = (endTime - this.startTime) / 1000.0;
        double throughput = this.messageCount / runTime;
        logPerformance(latency, throughput);
        this.messageCount = 0;
        this.startTime = System.currentTimeMillis();

    }

    /**
     * Logs average latency and throughput
     *
     * @param avgLatency average latency of the application
     * @param throughput throughput of the application
     */
    private void logPerformance(double avgLatency, double throughput) {
        log.info("--------------------------------------");
        log.info("Latency : " + avgLatency + " milli sec ");
        log.info("TPS :" + throughput + " req per sec");
        this.logger.log("\n--------------------------------------"
                + "\nLatency : " + avgLatency + " milli sec " +
                "\nTPS :" + throughput + " req per sec");
        this.performanceReport.add(new AnalyserReport(avgLatency, throughput));

        if (this.performanceReport.size() % 2 == 0) {
            int bef = this.performanceReport.size() - 2;
            int af = bef + 1;

            double befTps = this.performanceReport.get(bef).getTps();
            double afTps = this.performanceReport.get(af).getTps();

            double befLat = this.performanceReport.get(bef).getAvgLatency();
            double afLat = this.performanceReport.get(af).getAvgLatency();

            double befRat = (befTps / befLat);
            double afRat = (afTps / afLat);

            double tpsPerc = ((afTps - befTps) / befTps) * 100;
            double latPerc = ((befLat - afLat) / befLat) * 100;
            double ratPerc = ((befRat - afRat) / befRat) * 100;

            log.info("Effect on TPS : " + tpsPerc + "%");
            log.info("Effect on Latency : " + latPerc + "%");
            log.info("Effect on overall performance : " + ratPerc + "%");

            this.logger.close();
        }
    }
}


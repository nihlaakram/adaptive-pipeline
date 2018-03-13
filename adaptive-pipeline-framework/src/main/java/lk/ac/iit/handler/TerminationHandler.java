package lk.ac.iit.handler;

import lk.ac.iit.data.WorkLoadData;
import lk.ac.iit.usecase.usecase01.XMLMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

public class TerminationHandler implements Runnable {


    private BlockingQueue<XMLMessage> inQueue;
    private int messageCount;
    private long startTime;

    private static final Logger log = Logger.getLogger(TerminationHandler.class);



    /** Constructor
     *
     * @param inQueue the input queue
     * @param startTime the start time of the application
     */
    public TerminationHandler(LinkedBlockingQueue<XMLMessage> inQueue, long startTime) {
        this.inQueue = inQueue;
        this.startTime = startTime;
    }

    public void run() {

        long totalLatency = 0;
        while (true) {
            if (this.inQueue.size() > 0) {
                XMLMessage msg = this.inQueue.poll();

                if (msg.getTimestamp() == -WorkLoadData.scale()) {
                    calculatePerformance(totalLatency);
                    totalLatency = 0;
                } else if (msg.getTimestamp() == WorkLoadData.termination()) {
                    calculatePerformance(totalLatency);
                    break;
                }
                else {
                    this.messageCount++;
                    totalLatency += (System.currentTimeMillis() - msg.getTimestamp());
                }


            }


        }

    }

    private void calculatePerformance(long totalLatency){

        long endTime = System.currentTimeMillis();

        double latency = totalLatency / this.messageCount;
        double runTime = (endTime - this.startTime) / 1000.0;

        double throughput = this.messageCount / runTime;

        log.debug("Latency : " + latency + " milli sec ");
        log.info("TPS :" + throughput + " req per sec");
        log.info("Count : " + this.messageCount);

        this.messageCount =0;
        this.startTime = System.currentTimeMillis();

    }
}


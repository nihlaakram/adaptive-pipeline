package lk.ac.iit.handler;

import lk.ac.iit.data.PipeData;
import lk.ac.iit.data.WorkLoadData;
import lk.ac.iit.usecase.usecase01.XMLMessage;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PerformanceHandler implements Runnable {


    private static final Logger log = Logger.getLogger(PerformanceHandler.class);
    private BlockingQueue<PipeData> inQueue;
    private int messageCount;
    private long startTime;


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
                    totalLatency = 0;
                } else if (msg.getTimestamp() == WorkLoadData.termination()) {
                    calculatePerformance(totalLatency);
                    break;
                } else {
                    this.messageCount++;
                    totalLatency += (System.currentTimeMillis() - msg.getTimestamp());

//                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
//                    Transformer transformer = null;
//                    try {
//                        transformer = transformerFactory.newTransformer();
//                    } catch (TransformerConfigurationException e) {
//                        e.printStackTrace();
//                    }
//                    DOMSource source = new DOMSource(msg.getMessage());
//
//                    StreamResult result = new StreamResult(System.out);
//                    System.out.println();
//
//                    try {
//                        transformer.transform(source, result);
//                    } catch (TransformerException e) {
//                        e.printStackTrace();
//                    }
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
        //System.out.println(totalLatency);

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
        log.debug("Latency : " + avgLatency + " milli sec ");
        log.info("TPS :" + throughput + " req per sec");
        log.info("Count : " + this.messageCount);
    }
}


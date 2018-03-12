package lk.ac.iit.handler;

import lk.ac.iit.usecase.usecase01.XMLMessage;

import java.util.concurrent.LinkedBlockingQueue;

public class TerminationHandler implements Runnable {

    private LinkedBlockingQueue<XMLMessage> inQueue;
    private int messageCount;
    private long startTime;


    public TerminationHandler(LinkedBlockingQueue<XMLMessage> inQueue, long startTime, int messageCount) {
        this.inQueue = inQueue;
        this.startTime = startTime;

    }

    public void run() {

        long latencyTotal = 0;
        while (true) {


            //check queue size
            if (this.inQueue.size() > 0) {

                //take item from inQueue
                XMLMessage msg = this.inQueue.poll();

                //check if last element
                if (msg.getTimestamp() == -1) {
                    long endTime = System.currentTimeMillis();

                    double latency = latencyTotal / messageCount;
                    double runTime = (endTime - startTime) / 1000.0;

                    double throughput = messageCount / runTime;
                    System.out.println(" Latency : " + latency + " milli sec " +
                            "\n TPS :" + throughput + " req per sec" +
                            "\n Runtime :" + runTime + " s" +
                            "\n Count : " + messageCount);
                    latencyTotal = 0;
                    messageCount =0;

                } else if (msg.getTimestamp() == -2) {
                    long endTime = System.currentTimeMillis();

                    double latency = latencyTotal / messageCount;
                    double runTime = (endTime - startTime) / 1000.0;

                    double throughput = messageCount / runTime;
                    System.out.println(" Latency : " + latency + " milli sec " +
                            "\n TPS :" + throughput + " req per sec" +
                            "\n Runtime :" + runTime + " s" +
                            "\n Count : " + messageCount);
                    break;

                }
                else {
                    this.messageCount++;
                    latencyTotal += (System.currentTimeMillis() - msg.getTimestamp());
//                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
//                        Transformer transformer = null;
//                        try {
//                            transformer = transformerFactory.newTransformer();
//                            DOMSource source = new DOMSource(msg.getMessage());
//                            // Output to console for testing
//                            StreamResult result = new StreamResult(System.out);
//                            transformer.transform(source, result);
//                        } catch (TransformerConfigurationException e) {
//                            e.printStackTrace();
//                        } catch (TransformerException e) {
//                            e.printStackTrace();
//                        }
//                    System.out.println();



                }


            }


        }


    }
}

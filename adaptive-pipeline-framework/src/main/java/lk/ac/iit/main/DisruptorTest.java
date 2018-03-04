package lk.ac.iit.main;

import lk.ac.iit.data.StageData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.concurrent.LinkedBlockingQueue;

public class DisruptorTest {

    public static void main(String[] args) {

        System.out.println(Thread.activeCount());


    }
}


class SampleProducer1 extends Thread {
    LinkedBlockingQueue<StageData> in;

    public SampleProducer1(LinkedBlockingQueue<StageData> in) {
        this.in = in;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20000; i++) {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                // root elements
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("XML_MESSAGE");
                doc.appendChild(rootElement);
                //this.in.put(new StageData(2, new XMLmessage(doc, rootElement)));
                StageData data = new StageData(2, new Integer(1));
                //System.out.println("=="+data);
                this.in.put(data);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }

        }

        try {
            StageData data = new StageData(-1, null);
            data.setTerminate();
            this.in.put(data);
            System.out.println("Adding Termination");
            // this.in.put(new TerminationMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

class SampleStageHandler1 implements Runnable {

    LinkedBlockingQueue<StageData> out;
    LinkedBlockingQueue<StageData> in;

    public SampleStageHandler1(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue) {
        in = inQueue;
        out = outQueue;
    }

    public void run() {
        while (true) {


            if (in.size() > 0) {
                StageData val1 = in.poll();
                //System.out.println()
                if (val1.equals(null)) {
                    System.out.println("null");
                } else if (!val1.getTerminate()) {
//                    XMLmessage msg = (XMLmessage) val1.getDataObject();
//
//
//                    try {
//                        RandomStringGenerator random = new RandomStringGenerator.Builder()
//                                .withinRange('0', 'z').build();
//                        String charList = random.generate(100);
//                        msg.addToMessage(charList);
//                        val1.setTimestamp(1);
//                        // System.out.println(msg.getMessage()+"\t"+charList);
//                        out.put(val1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                } else {
                    try {
                        for (int i = 0; i < 5; i++) {
                            //getOutQueue().put(new TerminationMessage());
                            StageData data = new StageData(-1, null);
                            data.setTerminate();
                            out.put(data);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    break;
                }
//

            }
        }

        // System.out.println("Stage shutting down");


    }

}


//class Terminator extends HandlerStage {
//
//    static int count = 0;
//    private Monitor monitor;
//
//    public Terminator(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue, Monitor monitor) {
//        super(inQueue, outQueue);
//        this.monitor = monitor;
//    }
//
//    public void run() {
//
//        while (true) {
//            if (getInQueue().size() > 0) {
//                StageData val = this.getInQueue().poll();
//
//
//                if (val!= null ){
//                    System.out.println("null1");
//                } else if (!val.getTerminate() ) {
//                    val.setTimestamp(2);
//                    monitor.setTimestamp(val.getTimestamp());
//                } else {
//
//                    break;
//                }
//
//
//            }
//
//
//        }
//        System.out.println("Terminator shutting down");
//
//
//    }
//
//
//}



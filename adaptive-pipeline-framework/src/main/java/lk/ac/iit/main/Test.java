package lk.ac.iit.main;

import lk.ac.iit.core.Executor;
import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.stage.ProducerStage;
import org.apache.commons.text.RandomStringGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.concurrent.LinkedBlockingQueue;

public class Test {

    public static void main(String[] args) {

        int stageCount = 3;
        int learningThreshold = 10000;
        int maxThreads = 7;
        boolean isScale = true;
        boolean isVisualize = false;
        Monitor.initMonitor(stageCount, learningThreshold, maxThreads, isScale, isVisualize);
        Monitor monitor = Monitor.getMonitor();


        LinkedBlockingQueue<StageData> in = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> middle = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> out = new LinkedBlockingQueue<>();
        //producer


        HandlerStage term = new SampleTerminationStage(out, monitor);
        HandlerStage stage1 = new SampleHandlerStage1(middle, out);
        HandlerStage stage = new SampleHandlerStage(in, middle);
        ProducerStage producer = new SampleProducer(stageCount, maxThreads, in);


        monitor.getExecutor().addProducer(producer);
        monitor.getExecutor().addHandler(stage, stage1, term);
        monitor.getExecutor().start();


    }
}

 class SampleHandlerStage1 extends HandlerStage {

    public SampleHandlerStage1(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue) {
        super(inQueue, outQueue);
    }

    public void onEvent(StageData data) {


        XMLMessage msg = (XMLMessage) data.getDataObject();


        try {
            RandomStringGenerator random = new RandomStringGenerator.Builder()
                    .withinRange('0', 'z').build();
            String charList = random.generate(10);
            msg.addToMessage(charList);
            data.setTimestamp(2);
            // System.out.println(msg.getMessage()+"\t"+charList);
            getOutQueue().put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//

    }


}


//class SampleProducer extends Thread {
//    LinkedBlockingQueue<StageData> in;
//
//    public SampleProducer(LinkedBlockingQueue<StageData> in) {
//        this.in = in;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i <2000; i++) {
//            try {
//                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//
//                // root elements
//                Document doc = docBuilder.newDocument();
//                Element rootElement = doc.createElement("XML_MESSAGE");
//                doc.appendChild(rootElement);
//                //this.in.put(new StageData(2, new XMLmessage(doc, rootElement)));
//                this.in.put(new StageData(2, new Integer(999)));
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ParserConfigurationException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        try {
//            StageData data = new StageData(-1, null);
//            data.setTerminate();
//            this.in.put(data);
//            System.out.println("Adding Termination");
//            // this.in.put(new TerminationMessage());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//}
//
//class SampleStageHandler extends HandlerStage {
//
//    public SampleStageHandler(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue) {
//        super(inQueue, outQueue);
//    }
//
//    public void run() {
//        while (true) {
//
//
//            if (getInQueue().size() > 0) {
//                StageData val1 = getInQueue().poll();
//
//                try {
//
//                    if (!val1.getTerminate()) {
//                        Integer msg = (Integer) val1.getDataObject();
//
//
//                        try {
//
//                            val1.setTimestamp(1);
//                            // System.out.println(msg.getMessage()+"\t"+charList);
//                            getOutQueue().put(val1);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        try {
//                            for (int i = 0; i < 5; i++) {
//                                //getOutQueue().put(new TerminationMessage());
//                                StageData data = new StageData(-1, null);
//                                data.setTerminate();
//                                this.getOutQueue().put(data);
//                            }
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                        break;
//                    }
////
//                } catch (NullPointerException e) {
//                    //do nothing
//                }
//            }
//        }
//
//        System.out.println("Stage shutting down");
//
//
//    }
//
//}
//
//
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
//    public synchronized static int incCount() {
//        return count++;
//    }
//
//    public synchronized static int getCount() {
//        return count;
//    }
//
//    public void run() {
//
//        while (true) {
//            try {
//                if (getInQueue().size() > 0) {
//                    StageData val = this.getInQueue().poll();
//
//
//                    if (val.equals(null)) {
//                        System.out.println("null1");
//                    } else if (!val.getTerminate()) {
//                        System.out.print(val.getDataObject()+"\t");
//                        val.setTimestamp(2);
//                        monitor.setTimestamp(val.getTimestamp());
//                        incCount();
//                    } else {
//
//                        break;
//                    }
//
//
//                }
//
//            } catch (NullPointerException e){
//                //do nothing
//            }
//
//
//        }
//        System.out.println("Terminator shutting down"+getCount());
//
//
//    }
//
//
//}



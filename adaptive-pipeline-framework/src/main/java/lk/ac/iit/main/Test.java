//package lk.ac.iit.main;
//
//import lk.ac.iit.core.Executor;
//import lk.ac.iit.core.Monitor;
//import lk.ac.iit.data.StageData;
//import lk.ac.iit.data.HandlerStage;
//import lk.ac.iit.data.TerminationMessage;
//import lk.ac.iit.data.XMLMessage;
//import org.apache.commons.text.RandomStringGenerator;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import java.util.concurrent.LinkedBlockingQueue;
//
//public class Test {
//
//    public static void main(String[] args) {
//
//        int stageCount = 2;
//        //mape
//        Monitor.initMonitor(stageCount, 1000, 5, true, false);
//        Monitor monitor = Monitor.getMonitor();
//        // monitor.start();
//
//        LinkedBlockingQueue<StageData> in = new LinkedBlockingQueue<>();
//        LinkedBlockingQueue<StageData> out = new LinkedBlockingQueue<>();
//        //producer
//
//
//        Terminator term = new Terminator(out, null, monitor);
//        Thread t2 = new Thread(term);
//        t2.start();
//
//        SampleStageHandler stage = new SampleStageHandler(in, out);
//        Thread t1 = new Thread(stage);
//        t1.start();
//
//        monitor.getExecutor().addHandler(stage, term);
//
//        SampleProducer producer = new SampleProducer(in);
//        producer.start();
//
//
//    }
//}
//
//class XMLmessage  {
//    private Document message;
//    private Element rootNode;
//
//    public XMLmessage( Document message, Element root) {
//        this.message = message;
//        this.rootNode = root;
//    }
//
//
//
//    public Document getMessage() {
//        return message;
//    }
//
//    public void addToMessage(String message) {
//        Element element = this.message.createElement("RANDOM_CONTENT");
//        element.appendChild(this.message.createTextNode(message));
//        this.rootNode.appendChild(element);
//
//    }
//}
//
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
//
//

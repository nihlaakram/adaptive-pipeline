//package lk.ac.iit.main;
//
//import lk.ac.iit.core.Monitor;
//import lk.ac.iit.data.StageEvent;
//import lk.ac.iit.data.StageHandler;
//import org.apache.commons.text.RandomStringGenerator;
//
//import java.util.concurrent.LinkedBlockingQueue;
//
//
//class SampleEvent extends StageEvent {
//
//
//    public SampleEvent(int noOfStages, Object data) {
//        super(noOfStages, data);
//
//    }
//
//
//    public void appendToBody(String body) {
//        this.setDataObject(body);
//    }
//}
//
//class SampleProducer extends Thread {
//    LinkedBlockingQueue<StageEvent> in;
//
//    public SampleProducer(LinkedBlockingQueue<StageEvent> in) {
//        this.in = in;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 100000; i++) {
//            try {
//                this.in.put(new SampleEvent(3, new Integer(i)));
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        try {
//            for(int i =0; i<5; i++){
//                this.in.put(new SampleEvent(-1, null));
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//}
//
//class SampleStageHandler extends StageHandler {
//
//    public SampleStageHandler(LinkedBlockingQueue<StageEvent> inQueue, LinkedBlockingQueue<StageEvent> outQueue) {
//        super(inQueue, outQueue);
//    }
//
//    public void run() {
//        while (true) {
//
//
//            if (getInQueue().size() > 0) {
//                SampleEvent val1 = (SampleEvent) getInQueue().poll();
//                if (val1.getDataObject() != null) {
//
//
//                    val1.setTimestamp(1);
//                    try {
//                        RandomStringGenerator random = new RandomStringGenerator.Builder()
//                                .withinRange('0', 'z').build();
//                        String charList = random.generate(100);
//                        val1.appendToBody(charList);
//                        getOutQueue().put(val1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        for(int i =0; i<5; i++){
//                            getOutQueue().put(new SampleEvent(-1, null));
//                        }
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
//                }
////
//
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
//class SampleStageHandler1 extends StageHandler {
//
//    public SampleStageHandler1(LinkedBlockingQueue<StageEvent> inQueue, LinkedBlockingQueue<StageEvent> outQueue) {
//        super(inQueue, outQueue);
//    }
//
//    public void run() {
//        while (true) {
//
//
//            if (getInQueue().size() > 0) {
//                SampleEvent val1 = (SampleEvent) getInQueue().poll();
//                if (val1.getDataObject() != null) {
//
//
//                    val1.setTimestamp(2);
//                    try {
//                        RandomStringGenerator random = new RandomStringGenerator.Builder()
//                                .withinRange('0', 'z').build();
//                        String charList = random.generate(100);
//                        val1.appendToBody(charList);
//                        getOutQueue().put(val1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        for(int i =0; i<5; i++){
//                            getOutQueue().put(new SampleEvent(-1, null));
//                        }
//
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
//                }
////
//
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
//class Terminator extends StageHandler  {
//
//    private Monitor monitor;
//
//
//    public Terminator(LinkedBlockingQueue<StageEvent> inQueue, LinkedBlockingQueue<StageEvent> outQueue, Monitor monitor) {
//        super(inQueue, outQueue);
//        this.monitor = monitor;
//    }
//
//    public void run() {
//
//        while (true) {
//            if(getInQueue().size()>0){
//                SampleEvent val = (SampleEvent) this.getInQueue().poll();
//                if (val.getDataObject() != null) {
//                    RandomStringGenerator random = new RandomStringGenerator.Builder()
//                            .withinRange('0', 'z').build();
//                    String charList = random.generate(10);
//                    val.appendToBody(charList);
//                    val.setTimestamp(3);
//                    monitor.setTimestamp(val.getTimestamp());
//                } else {
//
//                    break;
//                }
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
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        int stageCount = 3;
//        //mape
//        Monitor.initMonitor(stageCount, 10000);
//        Monitor monitor = Monitor.getMonitor1();
//        // monitor.start();
//
//        LinkedBlockingQueue<StageEvent> in1 = new LinkedBlockingQueue<>();
//        LinkedBlockingQueue<StageEvent> in2 = new LinkedBlockingQueue<>();
//        LinkedBlockingQueue<StageEvent> in3 = new LinkedBlockingQueue<>();
//        //producer
//
//
//        Terminator term = new Terminator(in3, null, monitor);
//        Thread t2 = new Thread(term);
//        t2.start();
//
//        SampleStageHandler stage = new SampleStageHandler(in1, in2);
//        Thread t1 = new Thread(stage);
//        t1.start();
//
//        SampleStageHandler1 stage1 = new SampleStageHandler1(in2, in3);
//        Thread t3 = new Thread(stage1);
//        t3.start();
//
//        monitor.getExecutor().addHandler(stage, stage1, term);
//
//        SampleProducer producer = new SampleProducer(in1);
//        producer.start();
//
//
//
//
//    }
//}
//

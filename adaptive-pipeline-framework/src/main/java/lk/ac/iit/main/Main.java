//package lk.ac.iit.main;
//
//import lk.ac.iit.core.Producer;
//import lk.ac.iit.core.Stage_1;
//import lk.ac.iit.core.Terminator;
//import lk.ac.iit.data.XMLMessage;
//
//import java.util.concurrent.LinkedBlockingQueue;
//
//
//public class Main {
//
//    public static void main(String[] args) throws InterruptedException {
//
//        int cores = Runtime.getRuntime().availableProcessors();
//
//        System.out.println(cores);
//        //the message size
//        int messageSize = 100;
//
//        //the no. of messages to use
//        int messageCount = 1000;
//
//        //no of stages
//        int stageCount = 1;
//
//        //contribution from each stage to the string
//        int charCount = messageSize/stageCount;
//
//
//
//        //required queues
//        LinkedBlockingQueue<XMLMessage>[] queues = new LinkedBlockingQueue[stageCount+1];
//        for(int i=0; i<stageCount+1; i++){
//            queues[i] = new LinkedBlockingQueue<XMLMessage>();
//        }
//
//        //create the stages
//        Stage_1[] stages = new Stage_1[stageCount];
//        for(int i=0; i<stageCount; i++){
//            stages[i] = new Stage_1(queues[i], queues[i+1], charCount);
//        }
//
//        //start threads
//        Thread[] threads = new Thread[stageCount];
//        for(int i=0; i<stageCount; i++){
//            threads[i] = new Thread(stages[i]);
//            threads[i].start();
//        }
//
//
//
//        Terminator term = new Terminator(queues[stageCount], System.currentTimeMillis(), messageCount);
//        Thread t1 = new Thread(term);
//        t1.start();
//
//        //fill data
//        Producer prod = new Producer(queues[0], messageCount);
//        Thread t2 = new Thread(prod);
//        t2.start();
//
//
//    }
//}
//

package lk.ac.iit.main;
import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.data.StageHandler;

import java.util.concurrent.LinkedBlockingQueue;


class SampleData extends StageData {

    public SampleData(int noOfStages, Object data) {
        super(noOfStages, data);
    }
}

class SampleProducer extends Thread {
    LinkedBlockingQueue<StageData> in;

    public SampleProducer(LinkedBlockingQueue<StageData> in) {
        this.in = in;
    }

    @Override
    public void run() {
       for (int i = 0; true; i++) {

            try {
                this.in.put(new SampleData(2, new Integer(i)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}

class SampleStageHandler extends StageHandler {

    static int count = 0;

    public SampleStageHandler(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue) {
        super(inQueue, outQueue);
    }

    public void run() {
        while (true) {
            StageData val = getInQueue().poll();
            if (val != null) {
                count++;
//                if (count > 20000) {
//                    break;
//                }
                //System.out.println(val.getDataObject() + "\t" + val.getTimestamp()[0]);
                ;
                val.setTimestamp(1);
                //System.out.println(val.getDataObject() + "\t" + val.getTimestamp()[1] + "\n------------------");
                try {

                    getOutQueue().put(val);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        //System.out.println("Stage shutting down");


    }

}


class Terminator extends Thread {

    private LinkedBlockingQueue<StageData> inQueue;
    private LinkedBlockingQueue<StageData> outQueue;
    private Monitor monitor;

    static int count = 0;


    public Terminator(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue, Monitor monitor) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
        this.monitor = monitor;
    }

    public void run() {

        while (true) {
            StageData val = this.inQueue.poll();
            if (val != null) {
                count++;
                //System.out.println(count);
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println(val.getDataObject() + "\t" + val.getTimestamp()[0]);
                val.setTimestamp(2);
                monitor.setTimestamp(val.getTimestamp());
//                if (count == 20000) {
//                    //System.out.println("Terminator shutting down");
//                    break;
//                }
                //System.out.println(val.getDataObject() + "\t" + val.getTimestamp()[1] + "\n------------------");

            }

        }


    }

}

public class Main {

    public static void main(String[] args) {


        //mape
        Monitor.initMonitor(2, 1000);
        Monitor monitor = Monitor.getMonitor1();
        // monitor.start();

        LinkedBlockingQueue<StageData> in = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> out = new LinkedBlockingQueue<>();
        //producer


        Terminator term = new Terminator(out, null, monitor);
        term.start();

        SampleStageHandler stage = new SampleStageHandler(in, out);
       // stage.start();

        SampleProducer producer = new SampleProducer(in);
        producer.start();

        while (true){
            if (term.count>=1000){
                Terminator term1 = new Terminator(out, null, monitor);
                term1.start();
                break;
            }

        }




    }
}


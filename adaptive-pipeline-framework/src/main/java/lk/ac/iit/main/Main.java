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

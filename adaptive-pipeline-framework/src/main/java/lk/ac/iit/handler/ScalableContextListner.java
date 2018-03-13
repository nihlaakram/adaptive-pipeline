package lk.ac.iit.handler;

import lk.ac.iit.data.PipeData;

import java.util.concurrent.LinkedBlockingQueue;

public class ScalableContextListner {


    private Thread threads[] = null;
    private ScalableWorker[] runnable = null;
    private LinkedBlockingQueue<PipeData>[] queues = null;


//    public void contextInitialized(int workerCount, LinkedBlockingQueue<XMLMessage>[] queues) {
//
//
//    }
//

    public boolean contextDestroyed(int workerCount) {
        System.out.println("Stopping thread: " + threads);
        if (threads != null) {

            try {
                //thread.join
                for (int i = 0; i < workerCount; i++) {
                    System.out.println("Waiting for " + threads[i]);
                    threads[i].join();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread successfully stopped.");
            return true;
        }
        return false;
    }

    public void contextInitialized(LinkedBlockingQueue<PipeData> in, LinkedBlockingQueue<PipeData> out, int workerCount) {

        this.queues = new LinkedBlockingQueue[workerCount - 1];
        for (int i = 0; i < workerCount - 1; i++) {
            queues[i] = new LinkedBlockingQueue<>();
        }

        this.runnable = new ScalableWorker[workerCount];
        runnable[0] = new ScalableWorker(in, queues[0]);
        for (int i = 1; i < workerCount - 1; i++) {
            runnable[i] = new ScalableWorker(queues[i - 1], queues[i]);
        }
        runnable[workerCount - 1] = new ScalableWorker(queues[workerCount - 2], out);
        //     runnable[0] = new ScalableWorker(in, out);

        //start threads
        threads = new Thread[workerCount];
        for (int i = 0; i < workerCount; i++) {
            threads[i] = new Thread(runnable[i]);
            // System.out.println("Starting thread: " + threads[i]);
            threads[i].start();

        }

        /**
         LinkedBlockingQueue<XMLMessage>[] queues = new LinkedBlockingQueue[workerCount-1];
         for(int i=0; i<workerCount-1; i++){
         queues[i] = new LinkedBlockingQueue<>();
         }

         this.runnable = new ScalableWorker[workerCount];
         runnable[0] = new ScalableWorker(in, queues[0]);
         for(int i=1; i<workerCount-1; i++){
         runnable[i] = new ScalableWorker(queues[i-1], queues[i]);
         }
         runnable[workerCount-1] = new ScalableWorker(queues[0], out);
         //     runnable[0] = new ScalableWorker(in, out);

         System.out.println(runnable.length);
         for(int i=1; i<workerCount-1; i++){
         System.out.println(runnable[i]+"\t"+queues[i-1]+"\t"+queues[i]);
         }
         //start threads
         threads = new Thread[workerCount];
         for(int i=0; i<workerCount; i++){
         threads[i] = new Thread(runnable[i]);
         // System.out.println("Starting thread: " + threads[i]);
         threads[i].start();

         }
         */


//        runnable = new ScalableWorker();
//        thread = new Thread(runnable);


        System.out.println("Background process successfully started.");
    }
}


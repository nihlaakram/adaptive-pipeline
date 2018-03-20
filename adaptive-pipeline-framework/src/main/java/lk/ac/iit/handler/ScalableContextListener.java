package lk.ac.iit.handler;

import lk.ac.iit.data.PipeData;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.concurrent.LinkedBlockingQueue;

public class ScalableContextListener {

    private static final Logger log = Logger.getLogger(ScalableContextListener.class);
    private final LinkedBlockingQueue<PipeData> INPUT_QUEUE;
    private final LinkedBlockingQueue<PipeData> OUTPUT_QUEUE;
    private Thread threads[] = null;
    private ScalableWorker[] runnable = null;
    private LinkedBlockingQueue<PipeData>[] queues = null;
    private Class workerClass;

    /** Constructor
     *
     * @param inputQueue The input queue
     * @param outputQueue The output queue
     */
    public ScalableContextListener(LinkedBlockingQueue<PipeData> inputQueue, LinkedBlockingQueue<PipeData> outputQueue) {
        this.INPUT_QUEUE = inputQueue;
        this.OUTPUT_QUEUE = outputQueue;
    }


    /**
     *
     * @param workerCount The number of workers to be brought down
     * @return
     */
    public boolean scaleDown(int workerCount) {
        //log.info("Stopping thread: " + this.threads);
        if (this.threads != null) {

            try {
                for (int i = 0; i < workerCount; i++) {
                    this.threads[i].join();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //log.info("Thread successfully stopped.");
            log.info("Scaling down successful.");
            return true;
        }
        log.error("Scaling down failed.");
        return false;
    }


    /**
     *
     * @param workerCount the number of workers to be added
     */
    public void scaleUp(int workerCount) {

        this.queues = new LinkedBlockingQueue[workerCount - 1];
        this.runnable = new ScalableWorker[workerCount];

        if (workerCount == 1) {
            this.runnable[0] = new ScalableWorker(this.INPUT_QUEUE, this.OUTPUT_QUEUE);
            //this.runnable[0] = Class.forName(workerClass.getName())


        } else {
            for (int i = 0; i < workerCount - 1; i++) {
                this.queues[i] = new LinkedBlockingQueue<>();
            }
            this.runnable[0] = new ScalableWorker(this.INPUT_QUEUE, this.queues[0]);
            for (int i = 1; i < workerCount - 1; i++) {
                this.runnable[i] = new ScalableWorker(this.queues[i - 1], this.queues[i]);
            }
            this.runnable[workerCount - 1] = new ScalableWorker(this.queues[workerCount - 2], this.OUTPUT_QUEUE);
        }

        this.threads = new Thread[workerCount];

        for (int i = 0; i < workerCount; i++) {
            this.threads[i] = new Thread(this.runnable[i], "scaled-worker");
            this.threads[i].start();

        }

        log.info("Scaling up successful.");
    }

}


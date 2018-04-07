package lk.ac.iit.core.executor;

import lk.ac.iit.data.PipeData;
import lk.ac.iit.handler.ScalableWorker;
import lk.ac.iit.usecase.builder.handler.XMLScalableHandler;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.LinkedBlockingQueue;

public class ScalableContextListener {

    private static final Logger log = Logger.getLogger(ScalableContextListener.class);
    private final LinkedBlockingQueue<PipeData> INPUT_QUEUE;
    private final LinkedBlockingQueue<PipeData> OUTPUT_QUEUE;
    private Thread threads[] = null;
    private ScalableWorker[] runnable = null;
    private LinkedBlockingQueue<PipeData>[] queues = null;
    private Class workerClass = XMLScalableHandler.class;

    /**
     * Constructor
     *
     * @param inputQueue  The input queue
     * @param outputQueue The output queue
     */
    public ScalableContextListener(LinkedBlockingQueue<PipeData> inputQueue, LinkedBlockingQueue<PipeData> outputQueue) {
        this.INPUT_QUEUE = inputQueue;
        this.OUTPUT_QUEUE = outputQueue;
        //this.workerClass = SampleWorker.class;
    }


    /**
     * @param workerCount The number of workers to be brought down
     * @return is scaling down successful
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

            log.info("Scaling down successful.");
            return true;
        }
        log.error("Scaling down failed.");
        return false;
    }


    /**
     * @param workerCount the number of workers to be added
     */
    public void scaleUp(int workerCount) {

        this.queues = new LinkedBlockingQueue[workerCount - 1];
        this.runnable = new ScalableWorker[workerCount];

        Constructor constructor = null;
        try {
            System.out.println(workerClass.getName());
            constructor = workerClass.getDeclaredConstructor(LinkedBlockingQueue.class, LinkedBlockingQueue.class);
            if (workerCount == 1) {
                this.runnable[0] = (ScalableWorker) constructor.newInstance(this.INPUT_QUEUE, this.OUTPUT_QUEUE);


            } else {
                for (int i = 0; i < workerCount - 1; i++) {
                    this.queues[i] = new LinkedBlockingQueue<>();
                }
                this.runnable[0] = (ScalableWorker) constructor.newInstance(this.INPUT_QUEUE, this.queues[0]);
                for (int i = 1; i < workerCount - 1; i++) {
                    this.runnable[i] = (ScalableWorker) constructor.newInstance(this.queues[i - 1], this.queues[i]);
                }
                this.runnable[workerCount - 1] = (ScalableWorker) constructor.newInstance(this.queues[workerCount - 2],
                        this.OUTPUT_QUEUE);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        this.threads = new Thread[workerCount];

        for (int i = 0; i < workerCount; i++) {
            this.threads[i] = new Thread(this.runnable[i], "scaled-worker");
            this.threads[i].start();

        }

        log.info("Scaling up successful.");
    }


    public void addWorker(Class workerClass) {
        this.workerClass = workerClass;
    }
}


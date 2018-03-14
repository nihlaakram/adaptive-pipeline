package lk.ac.iit.data.queue;

import lk.ac.iit.data.PipeData;

import java.util.concurrent.LinkedBlockingQueue;

public class AdaptiveOutputQueue {

    private static LinkedBlockingQueue<PipeData> outQueue;
    private static AdaptiveOutputQueue outputQueue;


    /**
     * Constructor
     * <p>
     * Creates the output queue with maximum size
     */
    private AdaptiveOutputQueue() {
        this.outQueue = new LinkedBlockingQueue();
    }

    /**
     * Constructor
     * Creates the output queue with given size
     *
     * @param size the size of the queue
     */
    private AdaptiveOutputQueue(int size) {
        this.outQueue = new LinkedBlockingQueue(size);
    }

    /**
     * Creates a new instance of the queue or returns the existing instance for a given size.
     *
     * @param size the size of the queue
     * @return the queue
     */
    public synchronized static LinkedBlockingQueue<PipeData> getOutputQueue(int size) {
        if (outputQueue == null) {
            outputQueue = new AdaptiveOutputQueue(size);
        }
        return outQueue;
    }

    /**
     * Creates a new instance of the queue or returns the existing instance.
     *
     * @return the queue
     */
    public synchronized static LinkedBlockingQueue<PipeData> getOutputQueue() {
        if (outputQueue == null) {
            outputQueue = new AdaptiveOutputQueue();
        }
        return outQueue;
    }

}

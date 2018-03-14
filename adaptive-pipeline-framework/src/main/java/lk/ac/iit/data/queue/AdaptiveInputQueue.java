package lk.ac.iit.data.queue;

import lk.ac.iit.data.PipeData;

import java.util.concurrent.LinkedBlockingQueue;

public class AdaptiveInputQueue {

    private static LinkedBlockingQueue<PipeData> inQueue;
    private static AdaptiveInputQueue inputQueue;


    /**
     * Constructor
     * <p>
     * Creates the output queue with maximum size
     */
    private AdaptiveInputQueue() {
        this.inQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Constructor
     * Creates the output queue with given size
     *
     * @param size the size of the queue
     */
    private AdaptiveInputQueue(int size) {
        this.inQueue = new LinkedBlockingQueue(size);
    }


    /**
     * Creates a new instance of the queue or returns the existing instance for a given size.
     *
     * @param size the size of the queue
     * @return the queue
     */
    public synchronized static LinkedBlockingQueue<PipeData> getInputQueue(int size) {
        if (inputQueue == null) {
            inputQueue = new AdaptiveInputQueue(size);
        }
        return inQueue;
    }

    /**
     * Creates a new instance of the queue or returns the existing instance.
     *
     * @return the queue
     */
    public synchronized static LinkedBlockingQueue<PipeData> getInputQueue() {
        if (inputQueue == null) {
            inputQueue = new AdaptiveInputQueue();
        }
        return inQueue;
    }
}

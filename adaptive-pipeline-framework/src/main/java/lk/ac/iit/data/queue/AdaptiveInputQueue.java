package lk.ac.iit.data.queue;

import lk.ac.iit.data.Data_Single;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AdaptiveInputQueue {

    private static BlockingQueue<Data_Single> inQueue;
    private static AdaptiveInputQueue inputQueue;


    /** Constructor
     *
     * Creates the output queue with maximum size
     */
    private AdaptiveInputQueue(){
        this.inQueue = new LinkedBlockingQueue();
    }

    /** Constructor
     * Creates the output queue with given size
     *
     * @param size the size of the queue
     */
    private AdaptiveInputQueue(int size){
        this.inQueue = new LinkedBlockingQueue(size);
    }


    /** Creates a new instance of the queue or returns the existing instance for a given size.
     *
     * @param size the size of the queue
     * @return the queue
     */
    public synchronized static BlockingQueue<Data_Single> getInputQueue(int size) {
        if(inputQueue == null){
            inputQueue = new AdaptiveInputQueue(size);
        }
        return inQueue;
    }

    /** Creates a new instance of the queue or returns the existing instance.
     *
     * @return the queue
     */
    public synchronized static BlockingQueue<Data_Single> getInputQueue() {
        if(inputQueue == null){
            inputQueue = new AdaptiveInputQueue();
        }
        return inQueue;
    }
}

package lk.ac.iit.data.queue;

import lk.ac.iit.data.Data_Single;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AdaptiveOutputQueue {

    private static BlockingQueue<Data_Single> outQueue;
    private static AdaptiveOutputQueue outputQueue;


    /** Constructor
     *
     * Creates the output queue with maximum size
     */
    private AdaptiveOutputQueue(){
        this.outQueue = new LinkedBlockingQueue();
    }

    /** Constructor
     * Creates the output queue with given size
     * @param size the size of the queue
     */
    private AdaptiveOutputQueue(int size){
        this.outQueue = new LinkedBlockingQueue(size);
    }

    /** Creates a new instance of the queue or reurns the existing instance for a given size.
     *
     * @param size the size of the queue
     * @return the queue
     */
    public synchronized static BlockingQueue<Data_Single> getOutputQueue(int size) {
        if(outputQueue == null){
            outputQueue = new AdaptiveOutputQueue(size);
        }
        return outQueue;
    }

    /** Creates a new instance of the queue or reurns the existing instance.
     *
     * @return the queue
     */
    public synchronized static BlockingQueue<Data_Single> getOutputQueue() {
        if(outputQueue == null){
            outputQueue = new AdaptiveOutputQueue();
        }
        return outQueue;
    }

}

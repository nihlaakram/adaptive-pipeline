package lk.ac.iit.data.queue;

import lk.ac.iit.data.Data_Single;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AdaptiveInputQueue {

    private static BlockingQueue<Data_Single> inQueue;
    private static AdaptiveInputQueue inputQueue;

    private AdaptiveInputQueue(){
        this.inQueue = new LinkedBlockingQueue();
    }

    private AdaptiveInputQueue(int size){
        this.inQueue = new LinkedBlockingQueue(size);
    }


    public synchronized static BlockingQueue<Data_Single> getInputQueue(int size) {
        if(inputQueue == null){
            inputQueue = new AdaptiveInputQueue(size);
        }
        return inQueue;
    }

    public synchronized static BlockingQueue<Data_Single> getInputQueue() {
        if(inputQueue == null){
            inputQueue = new AdaptiveInputQueue();
        }
        return inQueue;
    }
}

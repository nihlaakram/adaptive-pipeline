package lk.ac.iit.data;

import java.util.concurrent.LinkedBlockingQueue;

public class StageHandler implements Cloneable, Runnable {


    private LinkedBlockingQueue<StageData> inQueue;
    private LinkedBlockingQueue<StageData> outQueue;


    public StageHandler(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
    }


    public LinkedBlockingQueue<StageData> getInQueue() {
        return inQueue;
    }

    public LinkedBlockingQueue<StageData> getOutQueue() {
        return outQueue;
    }

    @Override
    public void run() {
    }

    public StageHandler clone() throws
            CloneNotSupportedException {
        // Assign the shallow copy to new refernce variable t
        StageHandler t = (StageHandler) super.clone();

        //t.c = new StageHandler(getInQueue(), getOutQueue());

        // Create a new object for the field c
        // and assign it to shallow copy obtained,
        // to make it a deep copy
        return t;
    }

}

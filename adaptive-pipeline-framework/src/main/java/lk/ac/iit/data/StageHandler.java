package lk.ac.iit.data;

import java.util.concurrent.LinkedBlockingQueue;

public class StageHandler implements Cloneable, Runnable {


    private LinkedBlockingQueue<StageEvent> inQueue;
    private LinkedBlockingQueue<StageEvent> outQueue;


    public StageHandler(LinkedBlockingQueue<StageEvent> inQueue, LinkedBlockingQueue<StageEvent> outQueue) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
    }


    public LinkedBlockingQueue<StageEvent> getInQueue() {
        return inQueue;
    }

    public LinkedBlockingQueue<StageEvent> getOutQueue() {
        return outQueue;
    }

    @Override
    public void run() {
    }

    public StageHandler clone() {

        StageHandler t = null;
        try {
            t = (StageHandler) super.clone();
        } catch (CloneNotSupportedException e) {

        }

        return t;
    }

}

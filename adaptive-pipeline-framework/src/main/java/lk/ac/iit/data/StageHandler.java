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

    public StageHandler clone() {

        StageHandler t = null;
        try {
            t = (StageHandler) super.clone();
        } catch (CloneNotSupportedException e) {

        }

        return t;
    }

}

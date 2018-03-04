package lk.ac.iit.data;

import java.util.concurrent.LinkedBlockingQueue;

public class HandlerStage implements Cloneable, Runnable {


    private LinkedBlockingQueue<StageData> inQueue;
    private LinkedBlockingQueue<StageData> outQueue;


    public HandlerStage(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue) {
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
        while (true) {
            if (getInQueue().size() > 0) {
                try {
                    process();

                } catch (NullPointerException e) {
                    //do nothing
                }
            }
        }


    }


    private void terminate() {
    }

    public void process() {
    }

    public HandlerStage clone() {

        HandlerStage t = null;
        try {
            t = (HandlerStage) super.clone();
        } catch (CloneNotSupportedException e) {


        }

        return t;
    }

}

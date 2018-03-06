package lk.ac.iit.stage;

import lk.ac.iit.data.StageData;
import lk.ac.iit.data.TerminationMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class HandlerStage implements Cloneable, Runnable {


    private BlockingQueue<StageData> inQueue;
    private BlockingQueue<StageData> outQueue;


    public HandlerStage(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
    }

    public HandlerStage(LinkedBlockingQueue<StageData> inQueue) {
        this.inQueue = inQueue;
    }


    public BlockingQueue<StageData> getInQueue() {
        return inQueue;
    }

    public BlockingQueue<StageData> getOutQueue() {
        return outQueue;
    }

    @Override
    public void run() {
        while (true) {
            if (getInQueue().size() > 0) {
                try {
                    StageData data = getInQueue().poll();
                    if (!data.getTerminate()) {
                        onEvent(data);
                    } else {
                        terminate();
                        break;
                    }

                } catch (NullPointerException e) {
                    //do nothing
                }
            }
        }



    }

    private void terminate() {
        try {
            StageData data = new TerminationMessage();
            this.outQueue.put(data);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onEvent(StageData data) {
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
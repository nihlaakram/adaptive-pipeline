package lk.ac.iit.stage;

import lk.ac.iit.data.StageData;
import lk.ac.iit.data.TerminationMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class HandlerStage implements Cloneable, Runnable {


    private BlockingQueue<StageData> inQueue;
    private BlockingQueue<StageData> outQueue;
    private int stageID;


    public HandlerStage(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue, int stageID) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
        this.stageID = stageID;
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
                        data = onEvent(data);
                        data.setTimestamp(this.stageID);
                        getOutQueue().put(data);
                    } else {
                        terminate();
                        break;
                    }

                } catch (NullPointerException e) {
                    //do nothing
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

    public StageData onEvent(StageData data) {
        return  null;
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

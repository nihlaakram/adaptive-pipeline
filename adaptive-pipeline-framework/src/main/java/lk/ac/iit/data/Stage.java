package lk.ac.iit.data;

import java.util.concurrent.LinkedBlockingQueue;

public class Stage extends Thread {



    private LinkedBlockingQueue<StageData> inQueue;
    private LinkedBlockingQueue<StageData> outQueue;




    public Stage(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue) {
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
    public void run() { }

}

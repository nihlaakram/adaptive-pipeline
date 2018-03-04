package lk.ac.iit.stage;

import lk.ac.iit.data.StageData;
import lk.ac.iit.data.TerminationMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerStage extends Thread {
    private BlockingQueue<StageData> inQueue;
    private int maxThreads;
    private int noOfStages;

    public ProducerStage(int noOfStages, int maxThreads, LinkedBlockingQueue<StageData> in) {
        this.inQueue = in;
        this.maxThreads = maxThreads;
        this.noOfStages = noOfStages;
    }

    @Override
    public void run() {
        addInput();
        addTermination();


    }

    private void addTermination() {
        try {
            for (int i = 0; i < this.maxThreads; i++) {
                StageData data = new TerminationMessage();
                this.inQueue.put(data);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addInput() {
    }

    public BlockingQueue<StageData> getInQueue() {
        return this.inQueue;
    }

    public int getNoOfStages() {
        return this.noOfStages;
    }
}

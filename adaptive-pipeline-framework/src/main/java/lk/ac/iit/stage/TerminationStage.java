package lk.ac.iit.stage;

import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TerminationStage implements Runnable {



    private BlockingQueue<StageData> inQueue;
    private Monitor monitor;

    public TerminationStage(LinkedBlockingQueue<StageData> inQueue, Monitor monitor) {

        this.inQueue = inQueue;
        this.monitor = monitor;
    }


    @Override
    public void run() {
        while (true) {
            if (getInQueue().size() > 0) {
                try {
                    StageData data = getInQueue().poll();
                    if (!data.getTerminate()) {
                        onEvent(data);
                        monitor.setTimestamp(data.getTimestamp());
                    }

                } catch (NullPointerException e) {
                    //do nothing
                }
            }
        }
    }

    private void onEvent(StageData data) {
    }

    public BlockingQueue<StageData> getInQueue() {
        return inQueue;
    }
}

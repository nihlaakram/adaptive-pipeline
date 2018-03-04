package lk.ac.iit.main;

import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;

import java.util.concurrent.LinkedBlockingQueue;

public class SampleTerminationStage extends HandlerStage {

    static int count = 0;
    private Monitor monitor;

    public SampleTerminationStage(LinkedBlockingQueue<StageData> inQueue, Monitor monitor) {
        super(inQueue);
        this.monitor = monitor;
    }

    public synchronized static int incCount() {
        return count++;
    }

    public synchronized static int getCount() {
        return count;
    }

    public void onEvent(StageData data) {


                        data.setTimestamp(2);

                        monitor.setTimestamp(data.getTimestamp());
                        incCount();


    }


}


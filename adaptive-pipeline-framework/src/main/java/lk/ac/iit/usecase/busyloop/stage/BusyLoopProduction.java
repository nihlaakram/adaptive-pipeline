package lk.ac.iit.usecase.busyloop.stage;

import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.ProducerStage;
import lk.ac.iit.usecase.busyloop.data.BusyLoopData;

import java.util.concurrent.LinkedBlockingQueue;

public class BusyLoopProduction extends ProducerStage {
    public BusyLoopProduction(int noOfStages, int maxThreads, LinkedBlockingQueue<StageData> in) {
        super(noOfStages, maxThreads, in);
    }

    @Override
    public void addInput() {
        for (int i = 0; i < 20000; i++) {
            try {

                this.getInQueue().put(new StageData(getNoOfStages(), new BusyLoopData(100000), i));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}

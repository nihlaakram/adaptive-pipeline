package lk.ac.iit.usecase.usecase1;

import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.ProducerStage;

import java.util.concurrent.LinkedBlockingQueue;

public class SampleStage extends ProducerStage {
    public SampleStage(int stageCount, int maxThreads, LinkedBlockingQueue<StageData> b1) {
        super(stageCount, maxThreads, b1);

    }

    @Override
    public void addInput() {
        for (int i = 0; i < 20000; i++) {

            try {
                this.getInQueue().put(new StageData(getNoOfStages(), new SampleData(), i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }
}

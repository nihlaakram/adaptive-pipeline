package lk.ac.iit.usecase.usecase1.stage;

import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.TerminationStage;
import lk.ac.iit.usecase.usecase1.data.Message;

import java.util.concurrent.LinkedBlockingQueue;

public class SampleTerminationStage extends TerminationStage {



    public SampleTerminationStage(LinkedBlockingQueue<StageData> inQueue, Monitor monitor) {
        super(inQueue, monitor);

    }


    public void onEvent(StageData data) {
        Message msg = (Message) data.getDataObject();
    }


}



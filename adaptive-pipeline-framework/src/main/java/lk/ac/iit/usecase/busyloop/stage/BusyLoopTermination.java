package lk.ac.iit.usecase.busyloop.stage;

import lk.ac.iit.core.monitor.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.TerminationStage;
import lk.ac.iit.usecase.busyloop.data.BusyLoopData;

import java.util.concurrent.LinkedBlockingQueue;

public class BusyLoopTermination extends TerminationStage {


    public BusyLoopTermination(LinkedBlockingQueue<StageData> inQueue, Monitor monitor) {
        super(inQueue, monitor);

    }


    public void onEvent(StageData data) {
        BusyLoopData msg = (BusyLoopData) data.getDataObject();
    }
}
package lk.ac.iit.usecase.busyloop.stage;

import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.usecase.busyloop.data.BusyLoopData;

import java.util.concurrent.LinkedBlockingQueue;

public class BusyLoopStage2 extends HandlerStage {


    public BusyLoopStage2(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue, int stageID) {
        super(inQueue, outQueue, stageID);
    }


    public StageData onEvent(StageData data) {

        BusyLoopData msg = (BusyLoopData) data.getDataObject();
        //nothing
        return data;


    }


}

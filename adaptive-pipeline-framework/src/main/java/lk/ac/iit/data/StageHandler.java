package lk.ac.iit.data;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;

import java.util.concurrent.LinkedBlockingQueue;

public class StageHandler implements EventHandler<StageEvent> {


    private LinkedBlockingQueue<StageEvent> inQueue;
    private LinkedBlockingQueue<StageEvent> outQueue;


    public StageHandler(LinkedBlockingQueue<StageEvent> inQueue, LinkedBlockingQueue<StageEvent> outQueue) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
    }


    public LinkedBlockingQueue<StageEvent> getInQueue() {
        return inQueue;
    }

    public LinkedBlockingQueue<StageEvent> getOutQueue() {
        return outQueue;
    }

//    @Override
//    public void run() {
//    }
//
//    public StageHandler clone() {
//
//        StageHandler t = null;
//        try {
//            t = (StageHandler) super.clone();
//        } catch (CloneNotSupportedException e) {
//
//        }
//
//        return t;
//    }

    @Override
    public void onEvent(StageEvent stageEvent, long l, boolean b) throws Exception {

    }
}

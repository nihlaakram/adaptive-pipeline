package lk.ac.iit.data.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageEvent;

public abstract class FinalStageHandler implements EventHandler<StageEvent> {

    private static long num;
    private Monitor monitor;
    private long id;

    public FinalStageHandler(long handlerID, long num, Monitor monitor) {
        this.id = handlerID;
        this.num = num;
        this.monitor = monitor;
    }

    public synchronized static long getNum() {
        return FinalStageHandler.num;
    }

    public synchronized static void setNum(long num) {
        FinalStageHandler.num = num;
    }

    @Override
    public void onEvent(StageEvent stageEvent, long l, boolean b) throws Exception {
        if (stageEvent.getId() % this.getNum() == this.getId()) {
            process(stageEvent);
            this.monitor.setTimestamp(stageEvent.getTimestamp());
        }

    }

    public void process(StageEvent stageEvent) {
    }


    public long getId() {
        return this.id;
    }

}

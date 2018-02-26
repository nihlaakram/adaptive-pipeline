package lk.ac.iit.data.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageEvent;

public class FinalStageHandler implements EventHandler<StageEvent> {

    private static long num;
    public Monitor monitor;
    public long id;

    public FinalStageHandler(long id, long num, Monitor monitor) {
        this.id = id;
        this.num = num;
        this.monitor = monitor;
    }

    public synchronized static long getNum() {
        return num;
    }

    public synchronized static void setNum(long num) {
        FinalStageHandler.num = num;
    }

    @Override
    public void onEvent(StageEvent stageEvent, long l, boolean b) throws Exception {
        process(stageEvent);
    }

    public void process(StageEvent stageEvent) {
    }
}

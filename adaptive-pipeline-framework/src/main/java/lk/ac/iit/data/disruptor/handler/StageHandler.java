package lk.ac.iit.data.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageEvent;

public class StageHandler implements EventHandler<StageEvent> {

    private static long num;
    public Monitor monitor;
    public long id;

    public StageHandler(long handlerID, long num, Monitor monitor) {
        this.id = handlerID;
        this.num = num;
        this.monitor = monitor;
    }

    public synchronized static long getNum() {
        return num;
    }

    public synchronized static void setNum(long num) {
        StageHandler.num = num;
    }

    @Override
    public void onEvent(StageEvent stageEvent, long sequence, boolean b) throws Exception {
        //okay
        if (stageEvent.getId() % getNum() == this.id) {
            process(stageEvent);
            monitor.setTimestamp(stageEvent.getTimestamp());
        }


    }

    public void process(StageEvent inEvent) {
        //inEvent.setTimestamp(1);
        //return inEvent;
    }

}

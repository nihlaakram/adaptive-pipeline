package lk.ac.iit.data.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageEvent;

public class IntermediateStageHandler implements EventHandler<StageEvent> {


    public synchronized static long getNum() {
        return num;
    }

    public synchronized static void setNum(long num) {
        IntermediateStageHandler.num = num;
    }

    private static long num;
    public Monitor monitor;
    public RingBuffer<StageEvent> ring;
    public long id;



    public IntermediateStageHandler(long id, long num, Monitor monitor, RingBuffer<StageEvent> ringBuffer) {
        this.id = id;
        this.num = num;
        this.monitor = monitor;
        this.ring = ringBuffer;
    }


    @Override
    public void onEvent(StageEvent stageEvent, long l, boolean b) throws Exception {
        process( stageEvent, l);
    }


    public void process(StageEvent stageEvent, long sequence){}
}


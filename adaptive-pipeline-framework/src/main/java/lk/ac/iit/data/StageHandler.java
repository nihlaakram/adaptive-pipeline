package lk.ac.iit.data;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;

import java.util.concurrent.LinkedBlockingQueue;

public class StageHandler implements EventHandler<StageEvent> {


    private RingBuffer<StageEvent> ring;
    private static int num;
    private final int id;



    public StageHandler(int id, int num, RingBuffer ringBuffer) {
        this.id = id;
        this.num = num;
        this.ring = ringBuffer;
    }


    @Override
    public void onEvent(StageEvent stageEvent, long l, boolean b) throws Exception {
//        if (this.getId() % getNum() == this.id) {
//
//        }
    }
}

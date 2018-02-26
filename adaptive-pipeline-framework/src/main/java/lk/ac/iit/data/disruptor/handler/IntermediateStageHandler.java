package lk.ac.iit.data.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageEvent;


public class IntermediateStageHandler implements EventHandler<StageEvent> {


    private static long num;
    private RingBuffer<StageEvent> ring;
    private long id;
    private StageEvent event;



    public IntermediateStageHandler(long id, long num, RingBuffer<StageEvent> ringBuffer) {
        this.id = id;
        this.num = num;
        this.ring = ringBuffer;
    }


    @Override
    public void onEvent(StageEvent stageEvent, long sequence, boolean b) throws Exception {
        //okay

        if (stageEvent.getId() % this.getNum() == this.getId()) {
            //System.out.println(sequence+"\t--"+getNum());
            event = process(stageEvent);


            event = getRing().get(sequence);
            event.setBack(event.getId(), event);
            getRing().publish(sequence);
        }

    }


    public StageEvent process(StageEvent inEvent){
        //inEvent.setTimestamp(1);
        return inEvent;
    }

    public synchronized static long getNum() {
        return num;
    }

    public synchronized static void setNum(long num) {
        IntermediateStageHandler.num = num;
    }


    public RingBuffer<StageEvent> getRing() {
        return ring;
    }

    public long getId() {
        return id;
    }

}


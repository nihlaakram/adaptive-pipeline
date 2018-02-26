package lk.ac.iit.data.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageEvent;

public class FinalStageHandler implements EventHandler<StageEvent> {


    private static long num;
    private Monitor monitor;
    private long id;

    public FinalStageHandler(long handlerID, long num, Monitor monitor) {
        this.id = handlerID;
        this.num = num;
        this.monitor = monitor;
    }



    @Override
    public void onEvent(StageEvent stageEvent, long sequence, boolean b) throws Exception {
        //okay

        if (stageEvent.getId() % this.getNum() == this.id) {
            //System.out.println(sequence+"\t--"+getNum());
          //  System.out.println(sequence+"\t--"+getNum());

            process(stageEvent);

            monitor.setTimestamp(stageEvent.getTimestamp());
        }

    }


    public void process(StageEvent inEvent){
        //inEvent.setTimestamp(1);
//        return inEvent;
    }

    public synchronized static long getNum() {
        return num;
    }

    public synchronized static void setNum(long num) {
        FinalStageHandler.num = num;
    }

//
//    private static long num;
//    private static Monitor monitor;
//    private long id;
//
//    public FinalStageHandler(long handlerID, long num, Monitor monitor) {
//        this.id = handlerID;
//        this.num = num;
//        this.monitor = monitor;
//    }
//
//    public synchronized static long getNum() {
//        return num;
//    }
//
//    public synchronized static void setNum(long num) {
//        FinalStageHandler.num = num;
//    }
//
//    @Override
//    public void onEvent(StageEvent stageEvent, long sequence, boolean b) throws Exception {
//        //System.out.println(sequence+"\t--"+getNum());
//        if (stageEvent.getId() % this.getNum() == this.id) {
//            System.out.println(sequence+"\t--"+getNum());
//
//            stageEvent.setTimestamp(1);
//            stageEvent.setTimestamp(2);
//            //process(stageEvent);
//
//            monitor.setTimestamp(stageEvent.getTimestamp());
//        }
//
//    }
//
//    public void process(StageEvent stageEvent) {
//
//    }


}

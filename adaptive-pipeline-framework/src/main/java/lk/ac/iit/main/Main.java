package lk.ac.iit.main;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.disruptor.handler.FinalStageHandler;
import lk.ac.iit.data.StageEvent;
import lk.ac.iit.data.disruptor.handler.IntermediateStageHandler;
import lk.ac.iit.data.disruptor.StageEventFactory;
import lk.ac.iit.data.disruptor.handler.InitialStageHandler;
import lk.ac.iit.data.disruptor.handler.StageHandler;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class SampleHadler1 extends IntermediateStageHandler {
    public SampleHadler1(long id, long num, RingBuffer<StageEvent> ring) {
        super(id, num, ring);
    }

    @Override
    public StageEvent process(StageEvent stageEvent) {


            for (int i=0; i<10000; i++){
                i++;
                i--;
            }
            StageEvent event = stageEvent;
            event.setTimestamp(1);
            return event;

    }
}

class SampleHadler2 extends FinalStageHandler {
    public SampleHadler2(long id, long num,Monitor monitor) {
        super(id, num, monitor);
    }

    public void process(StageEvent stageEvent) {
        //System.out.println("Hello");

        stageEvent.setTimestamp(2);
    }
}
public class Main {

    public static void main(String[] args) throws InterruptedException {

        Monitor.initMonitor(2, 5000);

        //Monitor.getMonitor1();
        Monitor monitor1 = Monitor.getMonitor1();



        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();
        StageEventFactory factory = new StageEventFactory();
        int bufferSize = 256;

        // Construct the Disruptor
        Disruptor<StageEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);
        RingBuffer<StageEvent> ringBuffer = disruptor.getRingBuffer();

        IntermediateStageHandler[] arrHandler1 = new SampleHadler1[2];
        for (int i = 0; i < 2; i++) {
            arrHandler1[i] = new SampleHadler1(i, 1, ringBuffer);
        }

        FinalStageHandler[] arrHandler2 = new SampleHadler2[2];
        for (int i = 0; i < 2; i++) {
            arrHandler2[i] = new SampleHadler2(i, 1,  monitor1);
        }

        disruptor.handleEventsWith(arrHandler1);
        disruptor.after(arrHandler1).handleEventsWith(arrHandler2);
        monitor1.getExecutor().addHandler(arrHandler2);
        //disruptor.handleEventsWith(arrHandler2);
        disruptor.start();



        InitialStageHandler producer1 = new InitialStageHandler(ringBuffer);

        //System.out.println(Thread.activeCount());

        ByteBuffer bb = ByteBuffer.allocate(8);
        long time = System.currentTimeMillis();
        for (long l = 0; System.currentTimeMillis() < time+600; l++)

        {
            bb.putLong(0, l);
            producer1.onData(bb, l);
//            if (l == 500) {
//                Thread.sleep(1000);
//                Stage_1.setNum(3);
//                System.out.println("=======================");
//            }
////            if(l==1000){
////                Thread.sleep(1000);
////                Stage_1.setNum(2);
////                System.out.println("=======================");
////            }

        }
        disruptor.shutdown();
    }

}


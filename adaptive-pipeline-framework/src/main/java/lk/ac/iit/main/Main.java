package lk.ac.iit.main;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.disruptor.handler.FinalHandler;
import lk.ac.iit.data.StageEvent;
import lk.ac.iit.data.disruptor.handler.IntermediateHandler;
import lk.ac.iit.data.disruptor.StageEventFactory;
import lk.ac.iit.data.disruptor.StageProducer;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class SampleHadler1 extends IntermediateHandler {
    public SampleHadler1(long id, long num, Monitor monitor, RingBuffer<StageEvent> ring) {
        super(id, num, monitor, ring);
    }
    @Override
    public void process(StageEvent stageEvent, long sequence) {

        if (stageEvent.getId() % getNum() == this.id) {
            stageEvent.setTimestamp(1);
            StageEvent event = stageEvent;
            event = ring.get(sequence);
            event.setBack(event.getId(), event);
            ring.publish(sequence);
           // monitor.setTimestamp(stageEvent.getTimestamp());
        }
    }
}

class SampleHadler2 extends FinalHandler {
    public SampleHadler2(long id, long num, Monitor monitor) {
        super(id, num, monitor);
    }
    public void process(StageEvent stageEvent) {

        if (stageEvent.getId() % getNum() == this.id) {
            stageEvent.setTimestamp(2);
            monitor.setTimestamp(stageEvent.getTimestamp());
        }
    }
}
public class Main {

    public static void main(String[] args) throws InterruptedException {

        Monitor.initMonitor(2, 1000);

        //Monitor.getMonitor1();
        Monitor monitor1 = Monitor.getMonitor1();



        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();
        StageEventFactory factory = new StageEventFactory();
        int bufferSize = 256;

        // Construct the Disruptor
        Disruptor<StageEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);
        RingBuffer<StageEvent> ringBuffer = disruptor.getRingBuffer();

        IntermediateHandler[] arrHandler1 = new SampleHadler1[2];
        for (int i = 0; i < 2; i++) {
            arrHandler1[i] = new SampleHadler1(i, 1, monitor1, ringBuffer);
        }

        FinalHandler[] arrHandler2 = new SampleHadler2[2];
        for (int i = 0; i < 2; i++) {
            arrHandler2[i] = new SampleHadler2(i, 1, monitor1);
        }

        disruptor.handleEventsWith(arrHandler1);
        disruptor.after(arrHandler1).handleEventsWith(arrHandler2);
        monitor1.getExecutor().addHandler(arrHandler2);
        disruptor.start();



        StageProducer producer1 = new StageProducer(ringBuffer);

        System.out.println(Thread.activeCount());

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; l < 2000; l++)

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


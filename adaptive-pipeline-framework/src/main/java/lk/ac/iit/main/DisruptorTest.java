package lk.ac.iit.main;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.disruptor.handler.FinalHandler;
import lk.ac.iit.data.StageEvent;
import lk.ac.iit.data.disruptor.StageEventFactory;
import lk.ac.iit.data.disruptor.StageProducer;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class SampleHadler extends FinalHandler {
    public SampleHadler(long id, long num, Monitor monitor) {
        super(id, num, monitor);
    }

    public void process(StageEvent stageEvent) {
        //System.out.println(stageEvent.getId()+"\t"+this.num+"\t"+this.id+"\t"+(stageEvent.getId() % this.num == this.id));

//        if (stageEvent.getId() % num == this.id) {
//
//        }

        if (stageEvent.getId() % getNum() == this.id) {
            //System.out.println(id+ "\t"+ stageEvent.getId());
            stageEvent.setTimestamp(1);
//        stageEvent.setTimestamp(2);
            monitor.setTimestamp(stageEvent.getTimestamp());
        }
    }
}
public class DisruptorTest {

    public static void main(String[] args) throws InterruptedException {

        Monitor.initMonitor(1, 1000);

        //Monitor.getMonitor1();
        Monitor monitor1 = Monitor.getMonitor1();

        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();
        StageEventFactory factory = new StageEventFactory();
        int bufferSize = 256;

        // Construct the Disruptor
        Disruptor<StageEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);
        FinalHandler[] arrHandler = new SampleHadler[2];
        for (int i = 0; i < 2; i++) {
            arrHandler[i] = new SampleHadler(i, 1, monitor1);
        }
        disruptor.handleEventsWith(arrHandler);
        monitor1.getExecutor().addHandler(arrHandler);
        disruptor.start();

        RingBuffer<StageEvent> ringBuffer = disruptor.getRingBuffer();

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
    }
}


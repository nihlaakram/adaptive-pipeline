package lk.ac.iit.main;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lk.ac.iit.core.Producer1;
import lk.ac.iit.core.Stage_1;
import lk.ac.iit.data.LongEvent;
import lk.ac.iit.data.StageEvent;
import lk.ac.iit.data.disruptor.StageEventFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DistFullTest {

    public static void main(String [] args){
    // Executor that will be used to construct new threads for consumers
    Executor executor = Executors.newCachedThreadPool();
    StageEventFactory factory = new StageEventFactory();
    int bufferSize = 1024;

    // Construct the Disruptor
    Disruptor<StageEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);
    Stage_1[] arrHandler = new Stage_1[3];
        for (int i = 0; i < 3; i++) {
        arrHandler[i] = new Stage_1(i + "", i, 1);
    }
//        disruptor.handleEventsWith(arrHandler);
//
//    // Start the Disruptor, starts all threads running
//        disruptor.start();
//
//    // Get the ring buffer from the Disruptor to be used for publishing.
//    RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
//
//    Producer1 producer1 = new Producer1(ringBuffer);
//
//        System.out.println(Thread.activeCount());
//
//    ByteBuffer bb = ByteBuffer.allocate(8);
//        for (long l = 0; l < 1000; l++)
//
//    {
//        bb.putLong(0, l);
//        producer1.onData(bb, l);
//        if (l == 500) {
//            Thread.sleep(1000);
//            Stage_1.setNum(3);
//            System.out.println("=======================");
//        }
////            if(l==1000){
////                Thread.sleep(1000);
//                Stage_1.setNum(2);
//                System.out.println("=======================");
//            }

    }

}

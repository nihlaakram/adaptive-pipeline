import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lk.ac.iit.core.Producer;
import lk.ac.iit.core.Stage_1;
import lk.ac.iit.data.LongEvent;
import lk.ac.iit.data.StageData;
import lk.ac.iit.data.XMLMessage;
import org.apache.commons.text.RandomStringGenerator;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


class SampleData extends StageData{

    public SampleData(int noOfStages, Object data) {
        super(noOfStages, data);
    }
}

class SampleProducer extends Thread{
    LinkedBlockingQueue<SampleData> in;
    public SampleProducer( LinkedBlockingQueue<SampleData> in){
        this.in = in;
    }

    @Override
    public void run() {
        for(int i=0; i<1000;i++){
            try {
                this.in.put(new SampleData(1,new Integer(i)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

class SampleStage extends Thread{

    private LinkedBlockingQueue<SampleData> inQueue;
    private LinkedBlockingQueue<SampleData> outQueue;



    public void run() {
        while (true) {
            SampleData val = (SampleData) this.inQueue.poll();
            if(val!=null){
                System.out.println(val.getDataObject()+"\t"+val.getTimestamp()[0]);
                val.setTimestamp(1);
                System.out.println(val.getDataObject()+"\t"+val.getTimestamp()[1]+"\n------------------");
                try {
                    this.outQueue.put(val);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


    }


    public SampleStage(LinkedBlockingQueue<SampleData> inQueue, LinkedBlockingQueue<SampleData> outQueue) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
    }

}
public class FullTest {

    public static void main(String[] args) {

        LinkedBlockingQueue<SampleData> in = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<SampleData> out = new LinkedBlockingQueue<>();
        //producer
        SampleProducer producer = new SampleProducer(in);
        producer.start();

        SampleStage stage = new SampleStage(in, out);
        stage.start();



    }
}

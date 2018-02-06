import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;

import java.util.concurrent.LinkedBlockingQueue;


class SampleData extends StageData {

    public SampleData(int noOfStages, Object data) {
        super(noOfStages, data);
    }
}

class SampleProducer extends Thread {
    LinkedBlockingQueue<SampleData> in;

    public SampleProducer(LinkedBlockingQueue<SampleData> in) {
        this.in = in;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1001; i++) {
            try {
                this.in.put(new SampleData(1, new Integer(i)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

class SampleStage extends Thread {

    private LinkedBlockingQueue<SampleData> inQueue;
    private LinkedBlockingQueue<SampleData> outQueue;
    private Monitor monitor;


    public void run() {
        while (true) {
            SampleData val = this.inQueue.poll();
            if (val != null) {
                System.out.println(val.getDataObject() + "\t" + val.getTimestamp()[0]);
                val.setTimestamp(1);
                monitor.setTimestamp(new long[]{1,2});
                System.out.println(val.getDataObject() + "\t" + val.getTimestamp()[1] + "\n------------------");
                try {
                    this.outQueue.put(val);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


    }


    public SampleStage(LinkedBlockingQueue<SampleData> inQueue, LinkedBlockingQueue<SampleData> outQueue, Monitor monitor) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
        this.monitor = monitor;
    }

}

public class FullTest {

    public static void main(String[] args) {


        //mape
        Monitor.initMonitor(1, 10);
        Monitor monitor = Monitor.getMonitor1();
       // monitor.start();

        LinkedBlockingQueue<SampleData> in = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<SampleData> out = new LinkedBlockingQueue<>();
        //producer
        SampleProducer producer = new SampleProducer(in);
        producer.start();

        SampleStage stage = new SampleStage(in, out, monitor);
        stage.start();


    }
}

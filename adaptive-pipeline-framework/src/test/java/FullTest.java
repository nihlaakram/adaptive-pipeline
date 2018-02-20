import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.data.StageHandler;

import java.util.concurrent.LinkedBlockingQueue;


class SampleData extends StageData {

    public SampleData(int noOfStages, Object data) {
        super(noOfStages, data);
    }
}

class SampleProducer extends Thread {
    LinkedBlockingQueue<StageData> in;

    public SampleProducer(LinkedBlockingQueue<StageData> in) {
        this.in = in;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1001; i++) {
            try {
                this.in.put(new SampleData(2, new Integer(i)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}

class SampleStageHandler extends StageHandler {

    int count = 0;

    public SampleStageHandler(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue) {
        super(inQueue, outQueue);
    }

    public void run() {
        while (true) {
            StageData val = getInQueue().poll();
            if (val != null) {
                count++;
                if (count > 1000) {
                    break;
                }
                //System.out.println(val.getDataObject() + "\t" + val.getTimestamp()[0]);
                val.setTimestamp(1);
                //System.out.println(val.getDataObject() + "\t" + val.getTimestamp()[1] + "\n------------------");
                try {
                    getOutQueue().put(val);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        //System.out.println("Stage shutting down");


    }

}


class Terminator extends Thread {

    private LinkedBlockingQueue<StageData> inQueue;
    private LinkedBlockingQueue<StageData> outQueue;
    private Monitor monitor;


    public Terminator(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue, Monitor monitor) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
        this.monitor = monitor;
    }

    public void run() {
        int count = 0;
        while (true) {
            StageData val = this.inQueue.poll();
            if (val != null) {
                count++;
                //System.out.println(count);

                //System.out.println(val.getDataObject() + "\t" + val.getTimestamp()[0]);
                val.setTimestamp(2);
                monitor.setTimestamp(val.getTimestamp());
                if (count == 1000) {
                    //System.out.println("Terminator shutting down");
                    break;
                }
                //System.out.println(val.getDataObject() + "\t" + val.getTimestamp()[1] + "\n------------------");

            }

        }


    }

}

public class FullTest {

    public static void main(String[] args) {


        //mape
        Monitor.initMonitor(2, 1000);
        Monitor monitor = Monitor.getMonitor1();
        // monitor.start();

        LinkedBlockingQueue<StageData> in = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> out = new LinkedBlockingQueue<>();
        //producer


        Terminator term = new Terminator(out, null, monitor);
        term.start();

        SampleStageHandler stage = new SampleStageHandler(in, out);
        stage.start();

        SampleProducer producer = new SampleProducer(in);
        producer.start();


    }
}

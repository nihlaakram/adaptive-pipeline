package lk.ac.iit.main;

import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.stage.ProducerStage;

import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {

        int stageCount = 2;
        //mape
        Monitor.initMonitor(stageCount, 10000, 5, true, false);
        Monitor monitor = Monitor.getMonitor();
        // monitor.start();

        LinkedBlockingQueue<StageData> in = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> out = new LinkedBlockingQueue<>();
        //producer


        Terminator term = new Terminator(out, null, monitor);
        Thread t2 = new Thread(term);
        t2.start();

        HandlerStage stage = new SampleHandlerStage(in, out);
        Thread t1 = new Thread(stage);
        t1.start();

        monitor.getExecutor().addHandler(stage, term);

        ProducerStage producer = new SampleProducer(5, in);
        producer.start();


    }
}


class Terminator extends HandlerStage {

    static int count = 0;
    private Monitor monitor;

    public Terminator(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue, Monitor monitor) {
        super(inQueue, outQueue);
        this.monitor = monitor;
    }

    public synchronized static int incCount() {
        return count++;
    }

    public synchronized static int getCount() {
        return count;
    }

    public void run() {

        while (true) {
            try {
                if (getInQueue().size() > 0) {
                    StageData val = this.getInQueue().poll();


                    if (val.equals(null)) {
                        System.out.println("null1");
                    } else if (!val.getTerminate()) {
                        val.setTimestamp(2);
                        monitor.setTimestamp(val.getTimestamp());
                        incCount();
                    } else {

                        break;
                    }


                }

            } catch (NullPointerException e) {
                //do nothing
            }


        }
        System.out.println("Terminator shutting down" + getCount());


    }


}



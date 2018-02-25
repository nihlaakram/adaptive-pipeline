import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageEvent;
import lk.ac.iit.data.StageHandler;
import lk.ac.iit.data.TerminationMessage;

import java.util.concurrent.LinkedBlockingQueue;


class SampleEvent extends StageEvent {

    public SampleEvent(int noOfStages, Object data) {
        super(noOfStages, data);
    }
}

class SampleProducer extends Thread {
    LinkedBlockingQueue<StageEvent> in;

    public SampleProducer(LinkedBlockingQueue<StageEvent> in) {
        this.in = in;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            try {
                this.in.put(new SampleEvent(2, new Integer(i)));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        try {
            this.in.put(new TerminationMessage());
           // this.in.put(new TerminationMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

class SampleStageHandler extends StageHandler {

    public SampleStageHandler(LinkedBlockingQueue<StageEvent> inQueue, LinkedBlockingQueue<StageEvent> outQueue) {
        super(inQueue, outQueue);
    }

    public void run() {
        while (true) {


            if (getInQueue().size() > 0) {
                StageEvent val1 = getInQueue().poll();
                if (val1.getDataObject() != null) {


                    val1.setTimestamp(1);
                    try {
                        for(int i=0; i<100; i++){
                            //do nothing
                        }
                        getOutQueue().put(val1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        for(int i =0; i<5; i++){
                            getOutQueue().put(new TerminationMessage());
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    break;
                }
//

            }
        }

        System.out.println("Stage shutting down");


    }

}


class Terminator extends StageHandler  {

    private Monitor monitor;

    static int count =0;

    public Terminator(LinkedBlockingQueue<StageEvent> inQueue, LinkedBlockingQueue<StageEvent> outQueue, Monitor monitor) {
        super(inQueue, outQueue);
        this.monitor = monitor;
    }

    public void run() {

        while (true) {
            if(getInQueue().size()>0){
                StageEvent val = this.getInQueue().poll();
                if (val.getDataObject() != null) {
                    for(int i=0; i<10000; i++){
                        //do nothing
                    }
                    val.setTimestamp(2);
                    monitor.setTimestamp(val.getTimestamp());
                } else {

                    break;
                }
            }


        }
        System.out.println("Terminator shutting down");


    }


}

public class FullTest {

    public static void main(String[] args) {

        int stageCount = 2;
        //mape
        Monitor.initMonitor(stageCount, 10000);
        Monitor monitor = Monitor.getMonitor1();
        // monitor.start();

        LinkedBlockingQueue<StageEvent> in = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageEvent> out = new LinkedBlockingQueue<>();
        //producer


        Terminator term = new Terminator(out, null, monitor);
        Thread t2 = new Thread(term);
        t2.start();

        SampleStageHandler stage = new SampleStageHandler(in, out);
        Thread t1 = new Thread(stage);
        t1.start();

        monitor.getExecutor().addHandler(stage, term);

        SampleProducer producer = new SampleProducer(in);
        producer.start();




    }
}

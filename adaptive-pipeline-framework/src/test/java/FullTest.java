import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.data.StageHandler;
import lk.ac.iit.data.TerminationMessage;

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
        for (int i = 0; i < 6000; i++) {
            try {
                this.in.put(new SampleData(2, new Integer(i)));
                //System.out.println(i);
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

    public SampleStageHandler(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue) {
        super(inQueue, outQueue);
    }

    public void run() {
        while (true) {


            if (getInQueue().size() > 0) {
                StageData val1 = getInQueue().poll();
                if (val1.getDataObject() != null) {

                    //System.out.println(val1.getDataObject() + "\t" + val1.getTimestamp()[0]);
                    val1.setTimestamp(1);
                    try {

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
                    System.out.println(val1 + "\t" + getInQueue().size());
                    break;
                }
//

            }
        }

        System.out.println("Stage shutting down");


    }

}


class Terminator implements Cloneable, Runnable  {

    private LinkedBlockingQueue<StageData> inQueue;
    private LinkedBlockingQueue<StageData> outQueue;
    private Monitor monitor;

    static int count =0;

    public Terminator(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue, Monitor monitor) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
        this.monitor = monitor;
    }

    public void run() {

        while (true) {
            if(inQueue.size()>0){
                StageData val = this.inQueue.poll();
                if (val.getDataObject() != null) {
                    count++;
                    val.setTimestamp(2);
                    monitor.setTimestamp(val.getTimestamp());

                   // System.out.println(val.getDataObject() + "\t" + val.getTimestamp()[1] + "\n------------------");

                } else {
                    //System.out.println(val + "\t" + getInQueue().size());
                    break;
                }
            }


        }
        System.out.println("Terminator shutting down");


    }

    public Object clone() throws CloneNotSupportedException{
        Terminator t = (Terminator) super.clone();
        return  t;
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
        Thread t2 = new Thread(term);
        t2.start();

        SampleStageHandler stage = new SampleStageHandler(in, out);
        Thread t1 = new Thread(stage);
        t1.start();

        SampleProducer producer = new SampleProducer(in);
        producer.start();

        while (true){
            if (term.count>=1000){
                Terminator term1 = null;
                try {
                    term1 = (Terminator) term.clone();
                    Thread t = new Thread(term1);
                    t.start();

                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
            }

        }

        //System.out.println(term+"\t"+term1);




    }
}

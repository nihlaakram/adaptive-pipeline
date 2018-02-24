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
        for (int i = 0; i < 2000; i++) {
            try {
                this.in.put(new SampleData(2, new Integer(i)));

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

    public Terminator(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue, Monitor monitor) {
        super(inQueue, outQueue);
        this.monitor = monitor;
    }

    public void run() {

        while (true) {
            if(getInQueue().size()>0){
                StageData val = this.getInQueue().poll();
                if (val.getDataObject() != null) {
                    count++;
                    val.setTimestamp(2);
                    monitor.setTimestamp(val.getTimestamp());
                } else {

                   // break;
                }
            }


        }
       // System.out.println("Terminator shutting down");


    }


}

public class FullTest {

    public static void main(String[] args) {

        int stageCount = 2;
        //mape
        Monitor.initMonitor(stageCount, 1000);
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

        monitor.getExecutor().addHandler(stage, term);

        SampleProducer producer = new SampleProducer(in);
        producer.start();

//        while (true){
//            if (term.count>=1000){
//                StageHandler term1 = null;
//                try {
//                    term1 = (StageHandler) term.clone();
//                    Thread t = new Thread(term1);
//                    t.start();
//
//                } catch (CloneNotSupportedException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
//
//        }

        //System.out.println(term+"\t"+term1);




    }
}

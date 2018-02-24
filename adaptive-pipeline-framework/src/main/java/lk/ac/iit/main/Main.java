package lk.ac.iit.main;

import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.data.StageHandler;
import lk.ac.iit.data.TerminationMessage;
import org.apache.commons.text.RandomStringGenerator;

import java.util.concurrent.LinkedBlockingQueue;


class SampleData extends StageData {


    public SampleData(int noOfStages, Object data) {
        super(noOfStages, data);

    }


    public void appendToBody(String body) {
        this.setDataObject(body);
    }
}

class SampleProducer extends Thread {
    LinkedBlockingQueue<StageData> in;

    public SampleProducer(LinkedBlockingQueue<StageData> in) {
        this.in = in;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
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
                SampleData val1 = (SampleData) getInQueue().poll();
                if (val1.getDataObject() != null) {


                    val1.setTimestamp(1);
                    try {
                        RandomStringGenerator random = new RandomStringGenerator.Builder()
                                .withinRange('0', 'z').build();
                        String charList = random.generate(100);
                        val1.appendToBody(charList);
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


    public Terminator(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue, Monitor monitor) {
        super(inQueue, outQueue);
        this.monitor = monitor;
    }

    public void run() {

        while (true) {
            if(getInQueue().size()>0){
                SampleData val = (SampleData) this.getInQueue().poll();
                if (val.getDataObject() != null) {
                    RandomStringGenerator random = new RandomStringGenerator.Builder()
                            .withinRange('0', 'z').build();
                    String charList = random.generate(10);
                    val.appendToBody(charList);
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

public class Main {

    public static void main(String[] args) {

        int stageCount = 2;
        //mape
        Monitor.initMonitor(stageCount, 10000);
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




    }
}

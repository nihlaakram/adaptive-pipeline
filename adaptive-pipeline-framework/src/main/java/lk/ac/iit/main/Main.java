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


        HandlerStage term = new SampleTerminationStage(out, monitor);
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



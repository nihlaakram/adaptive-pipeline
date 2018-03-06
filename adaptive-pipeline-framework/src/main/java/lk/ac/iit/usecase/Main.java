package lk.ac.iit.main;

import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.stage.ProducerStage;
import lk.ac.iit.stage.TerminationStage;

import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {


        int stageCount = 2;
        //int messageSize = Integer.parseInt(args[0]);
        int messageSize = 10;
        int learningThreshold = 100000;//five hundred thousand
        int maxThreads = 10;
        boolean isScale = true;
        boolean isVisualize = false;
        Monitor.initMonitor(stageCount, learningThreshold, maxThreads, isScale, isVisualize);
        Monitor monitor = Monitor.getMonitor();


        LinkedBlockingQueue<StageData> b1 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b2 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b3 = new LinkedBlockingQueue<>();
        //producer


        TerminationStage term = new SampleTerminationStage(b3, monitor);
        HandlerStage stage2 = new SampleHandlerStage(b2, b3, messageSize, 2);
        HandlerStage stage1 = new SampleHandlerStage(b1, b2, messageSize, 1);

        ProducerStage producer = new SampleProducer(stageCount, maxThreads, b1);


        monitor.getExecutor().addProducer(producer);
        monitor.getExecutor().addTerminator(term);
        monitor.getExecutor().addHandler(stage1, stage2);
        monitor.getExecutor().start();




    }
}



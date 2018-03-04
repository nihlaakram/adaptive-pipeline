package lk.ac.iit.main;

import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.stage.ProducerStage;

import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {

        int stageCount = 2;
        int learningThreshold = 10000;
        int maxThreads = 5;
        boolean isScale = true;
        boolean isVisualize = false;
        Monitor.initMonitor(stageCount, learningThreshold, maxThreads, isScale, isVisualize);
        Monitor monitor = Monitor.getMonitor();


        LinkedBlockingQueue<StageData> in = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> out = new LinkedBlockingQueue<>();
        //producer


        HandlerStage term = new SampleTerminationStage(out, monitor);
        HandlerStage stage = new SampleHandlerStage(in, out);
        ProducerStage producer = new SampleProducer(stageCount, maxThreads, in);


        monitor.getExecutor().addProducer(producer);
        monitor.getExecutor().addHandler(stage, term);
        monitor.getExecutor().start();




    }
}



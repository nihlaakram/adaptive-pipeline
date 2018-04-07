package lk.ac.iit.usecase.busyloop;

import lk.ac.iit.core.monitor.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.stage.ProducerStage;
import lk.ac.iit.stage.TerminationStage;
import lk.ac.iit.usecase.busyloop.stage.BusyLoopProduction;
import lk.ac.iit.usecase.busyloop.stage.BusyLoopStage1;
import lk.ac.iit.usecase.busyloop.stage.BusyLoopStage2;
import lk.ac.iit.usecase.busyloop.stage.BusyLoopTermination;

import java.util.concurrent.LinkedBlockingQueue;

public class BusyLoopMain {
    public static void main(String[] args) {


        int stageCount = 2;

        int learningThreshold = 10000;
        int maxThreads = 10;
        boolean isScale = true;
        Monitor.initMonitor(stageCount, learningThreshold, maxThreads, isScale);
        Monitor monitor = Monitor.getMonitor();


        LinkedBlockingQueue<StageData> b1 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b2 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b3 = new LinkedBlockingQueue<>();
        //producer


        TerminationStage term = new BusyLoopTermination(b3, monitor);
        HandlerStage stage2 = new BusyLoopStage2(b2, b3, 2);
        HandlerStage stage1 = new BusyLoopStage1(b1, b2, 1);

        ProducerStage producer = new BusyLoopProduction(stageCount, maxThreads, b1);


        monitor.getExecutor().addProducer(producer);
        monitor.getExecutor().addTerminator(term);
        monitor.getExecutor().addHandler(stage1, stage2);
        monitor.getExecutor().start();


    }
}

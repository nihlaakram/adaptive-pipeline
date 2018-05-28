package lk.ac.iit.usecase.usecase1;

import lk.ac.iit.core.monitor.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.stage.ProducerStage;
import lk.ac.iit.stage.TerminationStage;
import lk.ac.iit.usecase.usecase1.stage.SampleHandlerStage;
import lk.ac.iit.usecase.usecase1.stage.SampleProducerStage;
import lk.ac.iit.usecase.usecase1.stage.SampleTerminationStage;

import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {


        int stageCount = 2;
        int messageSize = 100000;
        messageSize = messageSize / stageCount;

        switch (stageCount) {
            case 2:
                twoStages(stageCount, messageSize);
                break;
            case 3:
                threeStages(stageCount, messageSize);
                break;
            case 4:
                fourStages(stageCount, messageSize);
                break;
            case 5:
                fiveStages(stageCount, messageSize);
                break;
        }


    }

    private static void twoStages(int stageCount, int messageSize) {
        int learningThreshold = 10;//five hundred thousand
        int maxThreads = 10;
        boolean isScale = true;
        boolean isVisualize = false;
        Monitor.initMonitor(stageCount, learningThreshold, maxThreads, isScale);
        Monitor monitor = Monitor.getMonitor();


        LinkedBlockingQueue<StageData> b1 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b2 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b3 = new LinkedBlockingQueue<>();
        //producer


        TerminationStage term = new SampleTerminationStage(b3, monitor);
        HandlerStage stage2 = new SampleHandlerStage(b2, b3, messageSize, 2);
        HandlerStage stage1 = new SampleHandlerStage(b1, b2, messageSize, 1);

        ProducerStage producer = new SampleProducerStage(stageCount, maxThreads, b1);


        monitor.getExecutor().addProducer(producer);
        monitor.getExecutor().addTerminator(term);
        monitor.getExecutor().addHandler(stage1, stage2);
        monitor.getExecutor().start();

    }

    private static void threeStages(int stageCount, int messageSize) {
        int learningThreshold = 10;//five hundred thousand
        int maxThreads = 10;
        boolean isScale = true;
        boolean isVisualize = false;
        Monitor.initMonitor(stageCount, learningThreshold, maxThreads, isScale);
        Monitor monitor = Monitor.getMonitor();


        LinkedBlockingQueue<StageData> b1 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b2 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b3 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b4 = new LinkedBlockingQueue<>();
        //producer


        TerminationStage term = new SampleTerminationStage(b4, monitor);
        HandlerStage stage3 = new SampleHandlerStage(b3, b4, messageSize, 3);
        HandlerStage stage2 = new SampleHandlerStage(b2, b3, messageSize, 2);
        HandlerStage stage1 = new SampleHandlerStage(b1, b2, messageSize, 1);

        ProducerStage producer = new SampleProducerStage(stageCount, maxThreads, b1);


        monitor.getExecutor().addProducer(producer);
        monitor.getExecutor().addTerminator(term);
        monitor.getExecutor().addHandler(stage1, stage2, stage3);
        monitor.getExecutor().start();

    }

    private static void fourStages(int stageCount, int messageSize) {
        int learningThreshold = 10000;//five hundred thousand
        int maxThreads = 10;
        boolean isScale = true;
        boolean isVisualize = false;
        Monitor.initMonitor(stageCount, learningThreshold, maxThreads, isScale);
        Monitor monitor = Monitor.getMonitor();


        LinkedBlockingQueue<StageData> b1 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b2 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b3 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b4 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b5 = new LinkedBlockingQueue<>();
        //producer


        TerminationStage term = new SampleTerminationStage(b5, monitor);
        HandlerStage stage4 = new SampleHandlerStage(b4, b5, messageSize, 4);
        HandlerStage stage3 = new SampleHandlerStage(b3, b4, messageSize, 3);
        HandlerStage stage2 = new SampleHandlerStage(b2, b3, messageSize, 2);
        HandlerStage stage1 = new SampleHandlerStage(b1, b2, messageSize, 1);

        ProducerStage producer = new SampleProducerStage(stageCount, maxThreads, b1);


        monitor.getExecutor().addProducer(producer);
        monitor.getExecutor().addTerminator(term);
        monitor.getExecutor().addHandler(stage1, stage2, stage3, stage4);
        monitor.getExecutor().start();

    }


    private static void fiveStages(int stageCount, int messageSize) {
        int learningThreshold = 10000;//five hundred thousand
        int maxThreads = 10;
        boolean isScale = true;
        boolean isVisualize = false;
        Monitor.initMonitor(stageCount, learningThreshold, maxThreads, isScale);
        Monitor monitor = Monitor.getMonitor();


        LinkedBlockingQueue<StageData> b1 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b2 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b3 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b4 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b5 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b6 = new LinkedBlockingQueue<>();
        //producer


        TerminationStage term = new SampleTerminationStage(b6, monitor);
        HandlerStage stage5 = new SampleHandlerStage(b5, b6, messageSize, 5);
        HandlerStage stage4 = new SampleHandlerStage(b4, b5, messageSize, 4);
        HandlerStage stage3 = new SampleHandlerStage(b3, b4, messageSize, 3);
        HandlerStage stage2 = new SampleHandlerStage(b2, b3, messageSize, 2);
        HandlerStage stage1 = new SampleHandlerStage(b1, b2, messageSize, 1);

        ProducerStage producer = new SampleProducerStage(stageCount, maxThreads, b1);


        monitor.getExecutor().addProducer(producer);
        monitor.getExecutor().addTerminator(term);
        monitor.getExecutor().addHandler(stage1, stage2, stage3, stage4, stage5);
        monitor.getExecutor().start();

    }


}



package lk.ac.iit.usecase.usecase1;

import lk.ac.iit.core.monitor.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.stage.ProducerStage;
import lk.ac.iit.stage.TerminationStage;
import lk.ac.iit.usecase.usecase1.stage.SampleTerminationStage;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TestPipe {

    public static void main(String[] args) {


        int stageCount = 2;
        int messageSize = 10000;
        messageSize = messageSize / stageCount;

        int learningThreshold = 10000;//five hundred thousand
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
        HandlerStage stage2 = new Stage1(b2, b3, messageSize, 2);
        HandlerStage stage1 = new Stage2(b1, b2, messageSize, 1);

        ProducerStage producer = new SampleStage(stageCount, maxThreads, b1);


        monitor.getExecutor().addProducer(producer);
        monitor.getExecutor().addTerminator(term);
        monitor.getExecutor().addHandler(stage1, stage2);
        monitor.getExecutor().start();


    }

}

class Stage1 extends HandlerStage {

    private AtomicInteger messageSize;
    private int id;
    private int count = 0;

    public Stage1(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue,
                  int messagesize, int id) {
        super(inQueue, outQueue, id);
        this.messageSize = new AtomicInteger(messagesize);
        this.id = id;
    }

    public StageData onEvent(StageData data) {


        for (int i = 0; i < 1000000; i++) {

            //do nothing
        }
        return data;


    }

}

class Stage2 extends HandlerStage {

    private AtomicInteger messageSize;
    private int id;
    private int count = 0;

    public Stage2(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue,
                  int messagesize, int id) {
        super(inQueue, outQueue, id);
        this.messageSize = new AtomicInteger(messagesize);
        this.id = id;
    }

    public StageData onEvent(StageData data) {

        return data;


    }

}


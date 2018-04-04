package lk.ac.iit.handler;

import lk.ac.iit.core.analyser.workload.WorkLoadModel;
import lk.ac.iit.core.executor.ScalableContextListener;
import lk.ac.iit.data.PipeData;
import lk.ac.iit.data.queue.AdaptiveInputQueue;
import lk.ac.iit.data.queue.AdaptiveOutputQueue;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;

import java.util.concurrent.LinkedBlockingQueue;

public class JPipeScaler {

    private final LinkedBlockingQueue<PipeData> inputQueue;
    private final LinkedBlockingQueue<PipeData> outputQueue;
    private final WorkLoadModel workLoadModel;
    ScalableContextListener listner;
    ProductionHandler prod;
    private final PerformanceHandler performanceHandler;
    private boolean scale;
    private int workercount;


    public JPipeScaler(WorkLoadModel workLoadModel, boolean scale, int inQueueSize, int outQueueSize, int messageSize, int messageCount) {
        this.workLoadModel = workLoadModel;
        this.inputQueue = AdaptiveInputQueue.getInputQueue(inQueueSize);
        this.outputQueue = AdaptiveOutputQueue.getOutputQueue(outQueueSize);
        this.scale = scale;
        this.performanceHandler = new PerformanceHandler(this.inputQueue, System.currentTimeMillis());
        //  initPerformanceHandler();
    }

    public JPipeScaler(WorkLoadModel workLoadModel, boolean scale, int messageSize, int messageCount, int workerCount) {
        BasicConfigurator.configure(new ConsoleAppender(new PatternLayout(" [%t] %p - %m%n")));
        this.workLoadModel = workLoadModel;
        this.inputQueue = AdaptiveInputQueue.getInputQueue();
        this.outputQueue = AdaptiveOutputQueue.getOutputQueue();
        this.scale = scale;
        this.workercount = workerCount;

        //scale
        listner = new ScalableContextListener(this.inputQueue, this.outputQueue);
        listner.scaleUp(workerCount);

      //perf
        performanceHandler = new PerformanceHandler(this.outputQueue, System.currentTimeMillis());


        //fill data
        prod = new ProductionHandler( inputQueue, messageCount, messageSize, workerCount, listner, getWorkLoadModel());

    }

    public JPipeScaler(WorkLoadModel workLoadModel) {
        this.workLoadModel = workLoadModel;
        this.inputQueue = AdaptiveInputQueue.getInputQueue();
        this.outputQueue = AdaptiveOutputQueue.getOutputQueue();
        this.scale = false;

        this.performanceHandler = new PerformanceHandler(this.inputQueue, System.currentTimeMillis());
    }


    public WorkLoadModel getWorkLoadModel() {
        return workLoadModel;
    }

    public LinkedBlockingQueue<PipeData> getInputQueue() {
        return inputQueue;
    }

    public LinkedBlockingQueue<PipeData> getOutputQueue() {
        return outputQueue;
    }

    public boolean isScale() {
        return scale;
    }


    public void start() {
        initPerformanceHandler();
        initProductionHandler();
    }

    private void initPerformanceHandler() {
        Thread t1 = new Thread(this.performanceHandler, "performance");
        t1.start();
    }

    private void initProductionHandler() {
        Thread t2 = new Thread(this.prod, "producer");
        t2.start();
    }


}

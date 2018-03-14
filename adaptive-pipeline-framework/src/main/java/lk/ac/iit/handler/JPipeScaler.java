package lk.ac.iit.handler;

import lk.ac.iit.core.analyser.workload.WorkLoadModel;
import lk.ac.iit.data.PipeData;
import lk.ac.iit.data.queue.AdaptiveInputQueue;
import lk.ac.iit.data.queue.AdaptiveOutputQueue;

import java.util.concurrent.LinkedBlockingQueue;

public class JPipeScaler {

    private final LinkedBlockingQueue<PipeData> inputQueue;
    private final LinkedBlockingQueue<PipeData> outputQueue;
    private final WorkLoadModel workLoadModel;
    ScalableContextListener listner;
    ProductionHandler prod;
    //private final PerformanceHandler performanceHandler;
    private boolean scale;
    private int workercount;

    public JPipeScaler(WorkLoadModel workLoadModel, boolean scale, int inQueueSize, int outQueueSize, int messageSize, int messageCount) {
        this.workLoadModel = workLoadModel;
        this.inputQueue = AdaptiveInputQueue.getInputQueue(inQueueSize);
        this.outputQueue = AdaptiveOutputQueue.getOutputQueue(outQueueSize);
        this.scale = scale;
        //this.performanceHandler = new PerformanceHandler(this.inputQueue, System.currentTimeMillis());
        //  initPerformanceHandler();
    }

    public JPipeScaler(WorkLoadModel workLoadModel, boolean scale, int messageSize, int messageCount, int workerCount) {
        this.workLoadModel = workLoadModel;
        this.inputQueue = AdaptiveInputQueue.getInputQueue();
        this.outputQueue = AdaptiveOutputQueue.getOutputQueue();
        this.scale = scale;
        this.workercount = workerCount;

        //scale
        listner = new ScalableContextListener(this.inputQueue, this.outputQueue);
        listner.contextInitialized(workerCount);

//        //perf
//        this.performanceHandler = new PerformanceHandler(this.inputQueue, System.currentTimeMillis());

        //prod

        //create the stages


        //fill data
        prod = new ProductionHandler(inputQueue, messageCount, messageSize, workerCount, listner);

    }

    public JPipeScaler(WorkLoadModel workLoadModel) {
        this.workLoadModel = workLoadModel;
        this.inputQueue = AdaptiveInputQueue.getInputQueue();
        this.outputQueue = AdaptiveOutputQueue.getOutputQueue();
        this.scale = false;

//        this.performanceHandler = new PerformanceHandler(this.inputQueue, System.currentTimeMillis());
        // initPerformanceHandler();
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

//    private void initPerformanceHandler(){
//        Thread thread = new Thread(this.performanceHandler, "performance");
//        thread.start();
//    }


    public void start() {
        startProduction(this.prod);
    }

    private void initProductionHandler() {

    }

    private void initScalingHandler() {

        listner.contextInitialized(this.workercount);
    }

    public void startProduction(ProductionHandler productionHandler) {
        Thread t2 = new Thread(productionHandler);
        t2.start();
    }
}

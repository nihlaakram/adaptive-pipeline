package lk.ac.iit.handler;

import lk.ac.iit.core.analyser.workload.WorkLoadModel;
import lk.ac.iit.core.executor.ScalableContextListener;
import lk.ac.iit.core.monitor.JPipeMonitor;
import lk.ac.iit.data.PipeData;
import lk.ac.iit.data.queue.AdaptiveInputQueue;
import lk.ac.iit.data.queue.AdaptiveOutputQueue;
import lk.ac.iit.handler.production.ConstantRateProductionHandler;
import lk.ac.iit.handler.production.VariableRateProductionHandler;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;

import java.util.concurrent.LinkedBlockingQueue;

public class JPipeScaler {

    private final LinkedBlockingQueue<PipeData> inputQueue;
    private final LinkedBlockingQueue<PipeData> outputQueue;
    private final WorkLoadModel workLoadModel;
    Runnable prod;
    private JPipeMonitor monitor;
    private PerformanceHandler performanceHandler;
    private boolean scale;
    private int workercount;
    private Class workerClass;
    private int messageCount;
    private int messageSize;


    public JPipeScaler(WorkLoadModel workLoadModel, boolean scale, int messageSize, int messageCount, int workerCount) {
        BasicConfigurator.configure(new ConsoleAppender(new PatternLayout(" [%t] %p - %m%n")));
        this.workLoadModel = workLoadModel;
        this.inputQueue = AdaptiveInputQueue.getInputQueue();
        this.outputQueue = AdaptiveOutputQueue.getOutputQueue();
        this.scale = scale;
        this.workercount = workerCount;
        this.messageCount = messageCount;
        this.messageSize = messageSize;

        this.monitor = new JPipeMonitor(new ScalableContextListener(this.inputQueue, this.outputQueue));


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

        handleMonitor();
        //perf
        this.performanceHandler = new PerformanceHandler(getOutputQueue(), System.currentTimeMillis());


        //fill data
        this.prod = new ConstantRateProductionHandler(getInputQueue(), this.messageCount, this.messageSize, this.workercount, this.monitor.getListener(), getWorkLoadModel());

        initPerformanceHandler();
        initProductionHandler();
    }

    public void start(int i) {

        handleMonitor();
        //perf
        this.performanceHandler = new PerformanceHandler(getOutputQueue(), System.currentTimeMillis());


        //fill data
        this.prod = new VariableRateProductionHandler(getInputQueue(), this.messageCount, this.messageSize, this.workercount, this.monitor.getListener(), getWorkLoadModel());

        initPerformanceHandler();
        initProductionHandler();
    }

    private void handleMonitor() {
        this.monitor.getListener().addWorker(this.workerClass);
        this.monitor.getListener().scaleUp(this.workercount);
    }

    private void initPerformanceHandler() {
        Thread t1 = new Thread(this.performanceHandler, "performance");
        t1.start();
    }

    private void initProductionHandler() {
        Thread t2 = new Thread(this.prod, "producer");
        t2.start();
    }


    public void addWorker(Class clazz) {
        this.workerClass = clazz;
    }

}


/**
 * Copyright 2018, Nihla Akram
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
    private Runnable prod;
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
        this.monitor.getListener().start(this.workercount);
    }

    private void initPerformanceHandler() {
        Thread t1 = new Thread(this.performanceHandler, "performance-handler");
        t1.start();
    }

    private void initProductionHandler() {
        Thread t2 = new Thread(this.prod, "production-handler");
        t2.start();
    }


    public void addWorker(Class clazz) {
        this.workerClass = clazz;
    }

}


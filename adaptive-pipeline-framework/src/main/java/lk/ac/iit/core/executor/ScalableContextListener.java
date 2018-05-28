/**
 * Copyright 2018, Nihla Akram
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package lk.ac.iit.core.executor;

import lk.ac.iit.data.PipeData;
import lk.ac.iit.handler.ScalableWorker;
import lk.ac.iit.usecase.builder.handler.XMLScalableHandler;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.LinkedBlockingQueue;

public class ScalableContextListener {

    private static final Logger log = Logger.getLogger(ScalableContextListener.class);
    private final LinkedBlockingQueue<PipeData> INPUT_QUEUE;
    private final LinkedBlockingQueue<PipeData> OUTPUT_QUEUE;
    private Thread threads[] = null;
    private ScalableWorker[] runnable = null;
    private LinkedBlockingQueue<PipeData>[] queues = null;
    private Class workerClass = XMLScalableHandler.class;

    /**
     * Constructor
     *
     * @param inputQueue  The input queue
     * @param outputQueue The output queue
     */
    public ScalableContextListener(LinkedBlockingQueue<PipeData> inputQueue, LinkedBlockingQueue<PipeData> outputQueue) {
        this.INPUT_QUEUE = inputQueue;
        this.OUTPUT_QUEUE = outputQueue;
        //this.workerClass = SampleWorker.class;
    }


    /**
     * @param workerCount The number of workers to be brought down
     * @return is scaling down request successful
     */
    public boolean scaleDown(int workerCount) {

        //log.info("Reinitialze .");
            if (this.threads != null) {
                try {
                    for (int i = 0; i < workerCount; i++) {
                        this.threads[i].join();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //log.info("Scaling down successful.");
                return true;
            }
            log.error("Scaling down failed.");
            return false;
        }



        /**
         * @param workerCount the number of workers to be added
         */
    public void scaleUp(int workerCount) {
        log.info("--------------------------------------");
        log.info("Initializing scaling");
        log.info("Requesting scaling upto : "+workerCount+" stages");
        execute(workerCount);
        log.info("Scaling up successful.");
    }


    public void execute(int workerCount) {

        this.queues = new LinkedBlockingQueue[workerCount - 1];
        this.runnable = new ScalableWorker[workerCount];

        Constructor constructor = null;
        try {
            constructor = workerClass.getDeclaredConstructor(LinkedBlockingQueue.class, LinkedBlockingQueue.class);
            if (workerCount == 1) {
                this.runnable[0] = (ScalableWorker) constructor.newInstance(this.INPUT_QUEUE, this.OUTPUT_QUEUE);


            } else {
                for (int i = 0; i < workerCount - 1; i++) {
                    this.queues[i] = new LinkedBlockingQueue<>();
                }
                this.runnable[0] = (ScalableWorker) constructor.newInstance(this.INPUT_QUEUE, this.queues[0]);
                for (int i = 1; i < workerCount - 1; i++) {
                    this.runnable[i] = (ScalableWorker) constructor.newInstance(this.queues[i - 1], this.queues[i]);
                }
                this.runnable[workerCount - 1] = (ScalableWorker) constructor.newInstance(this.queues[workerCount - 2],
                        this.OUTPUT_QUEUE);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        this.threads = new Thread[workerCount];

        for (int i = 0; i < workerCount; i++) {
            this.threads[i] = new Thread(this.runnable[i], "scaled-worker-"+i);
            this.threads[i].start();

        }

    }


    public void addWorker(Class workerClass) {
        this.workerClass = workerClass;
    }

    public void start(int workerCount){
        log.info("--------------------------------------");
        log.info("Initializing Pipeline.");
        execute(workerCount);
    }
}



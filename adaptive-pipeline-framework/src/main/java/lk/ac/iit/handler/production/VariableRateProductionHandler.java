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

package lk.ac.iit.handler.production;

import lk.ac.iit.core.analyser.workload.WorkLoadModel;
import lk.ac.iit.core.executor.ScalableContextListener;
import lk.ac.iit.data.PipeData;
import lk.ac.iit.usecase.builder.handler.XMLMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.concurrent.LinkedBlockingQueue;

public class VariableRateProductionHandler implements Runnable {

    private LinkedBlockingQueue<PipeData> inQueue;
    private int messageCount;
    private int workload;
    private int workers;
    private ScalableContextListener listener;
    private WorkLoadModel model;

    public VariableRateProductionHandler(LinkedBlockingQueue<PipeData> inQueue,
                                         int messageCount, int workload, int workers, ScalableContextListener listener,
                                         WorkLoadModel model) {
        this.inQueue = inQueue;
        this.messageCount = messageCount;
        this.workload = workload;
        this.workers = workers;
        this.listener = listener;
        this.model = model;
    }

    private void generate (int sleep, int messageCount){
        for (int i = 0; i < messageCount; i++) {
            if (i % 100 == 0) {
                try {
                    Thread.sleep(sleep / 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            populate();
        }
    }

    @Override
    public void run() {


        try {


            //workload 1000req/sec
            int sleep_1 = 100000;
            for (int i = 0; i < 5000; i++) {
                if (i != 0 && i % 100 == 0) {
                    Thread.sleep(sleep_1 / 10);
                }
                populate();
            }
            this.inQueue.put(new XMLMessage(-1, null, null, this.workload));

            boolean init = this.listener.scaleDown(1);
            this.workers = model.getWorkers(10, this.workload);
            if (init) {
                this.listener.scaleUp(this.workers);
                for (int i = 0; i < 5000; i++) {
                    if (i != 0 && i % 100 == 0) {
                        Thread.sleep(sleep_1 / 10);
                    }
                    populate();

                }
            }

            this.inQueue.put(new XMLMessage(-1, null, null, this.workload));

            init = this.listener.scaleDown(this.workers);

            // workload 10req/sec
            System.out.println("Change in workload -----------");
            this.workers = 1;
            this.listener.scaleUp(this.workers);
            int sleep_2 = 1000;
            for (int i = 0; i < 10000; i++) {
                if (i != 0 && i % 100 == 0) {

                    Thread.sleep(sleep_2 / 10);
                }
                populate();
            }
            this.inQueue.put(new XMLMessage(-1, null, null, this.workload));

            init = this.listener.scaleDown(this.workers);
            this.workers = model.getWorkers(1000, this.workload);
            if (init) {
                this.listener.scaleUp(this.workers);
                for (int i = 0; i < 10000; i++) {
                    if (i != 0 && i % 100 == 0) {
                        Thread.sleep(sleep_2 / 10);
                    }
                    populate();

                }
            }


            this.inQueue.put(new XMLMessage(-2, null, null, this.workload));
            this.listener.scaleDown(this.workers);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void populate() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("XML_MESSAGE");
        doc.appendChild(rootElement);

        try {
            this.inQueue.put(new XMLMessage(System.currentTimeMillis(), doc, rootElement, this.workload / this.workers));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



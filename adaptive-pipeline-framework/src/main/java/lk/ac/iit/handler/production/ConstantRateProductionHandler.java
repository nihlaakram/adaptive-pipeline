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

public class ConstantRateProductionHandler implements Runnable {

    public LinkedBlockingQueue<PipeData> inQueue;
    private int messageCount;
    private int workload;
    private int workers;
    private ScalableContextListener listner;
    private WorkLoadModel model;

    public ConstantRateProductionHandler(LinkedBlockingQueue<PipeData> inQueue,
                                         int messageCount, int workload, int workers, ScalableContextListener listner,
                                         WorkLoadModel model) {
        this.inQueue = inQueue;
        this.messageCount = messageCount;
        this.workload = workload;
        this.workers = workers;
        this.listner = listner;
        this.model = model;
    }

    @Override
    public void run() {


        try {

            for (int i = 0; i < messageCount; i++) {
                populate();
            }
            this.inQueue.put(new XMLMessage(-1, null, null, this.workload));

            boolean destroyed = this.listner.scaleDown(this.workers);
            this.workers = model.getWorkers(this.workload);
            //System.out.println(workers);
            if (destroyed) {
                this.listner.scaleUp(this.workers);
                for (int i = 0; i < messageCount; i++) {
                    populate();

                }
            }

            this.inQueue.put(new XMLMessage(-2, null, null, this.workload));
            this.listner.scaleDown(this.workers);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void populate() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder1 = null;
        try {
            docBuilder1 = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


        Document doc = docBuilder1.newDocument();
        Element rootElement = doc.createElement("XML_MESSAGE");
        doc.appendChild(rootElement);

        try {
            this.inQueue.put(new XMLMessage(System.currentTimeMillis(), doc, rootElement, this.workload / this.workers));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


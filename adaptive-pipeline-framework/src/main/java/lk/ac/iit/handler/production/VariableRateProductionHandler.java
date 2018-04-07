package lk.ac.iit.handler.production;

import lk.ac.iit.core.analyser.workload.WorkLoadModel;
import lk.ac.iit.core.executor.ScalableContextListener;
import lk.ac.iit.data.PipeData;
import lk.ac.iit.usecase.usecase01.XMLMessage;
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

    @Override
    public void run() {


        try {
            //workload 1000req/sec
            int sleep_1 = 1000;
            for (int i = 0; i < this.messageCount; i++) {
                if (i != 0 && i % 100 == 0) {
                    Thread.sleep(sleep_1 / 10);
                }
                populate();
            }
            this.inQueue.put(new XMLMessage(-1, null, null, this.workload));

            boolean init = this.listener.scaleDown(1);
            this.workers = model.getWorkers((1000 / (sleep_1 / 1000)), this.workload);
            if (init) {
                this.listener.scaleUp(this.workers);
                for (int i = 0; i < this.messageCount; i++) {
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
            int sleep_2 = 100000;
            for (int i = 0; i < 5000; i++) {
                if (i != 0 && i % 100 == 0) {

                    Thread.sleep(sleep_2 / 10);
                }
                populate();
            }
            this.inQueue.put(new XMLMessage(-1, null, null, this.workload));

            init = this.listener.scaleDown(this.workers);
            this.workers = model.getWorkers((1000 / (sleep_2 / 1000)), this.workload);
            if (init) {
                this.listener.scaleUp(this.workers);
                for (int i = 0; i < 5000; i++) {
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



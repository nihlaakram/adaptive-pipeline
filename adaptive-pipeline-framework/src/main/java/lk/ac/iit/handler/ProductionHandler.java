package lk.ac.iit.handler;

import lk.ac.iit.core.analyser.workload.WorkLoadModel;
import lk.ac.iit.data.PipeData;
import lk.ac.iit.usecase.usecase01.XMLMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.concurrent.LinkedBlockingQueue;

public class ProductionHandler implements Runnable {

    LinkedBlockingQueue<PipeData> inQueue;
    int messageCount;
    int workload;
    int workers;
    ScalableContextListener listner;
    WorkLoadModel model;

    public ProductionHandler(LinkedBlockingQueue<PipeData> inQueue,
                             int messageCount, int workload, int workers, ScalableContextListener listner,
                             WorkLoadModel model) {
        this.inQueue = inQueue;
        this.messageCount = messageCount;
        this.workload = workload;
        this.workers = workers;
        this.listner = listner;
        this.model=model;
    }

    @Override
    public void run() {


        try {
            for (int i = 0; i < messageCount; i++) {
                populate();
            }
            this.inQueue.put(new XMLMessage(-1, null, null, this.workload));

            boolean destroyed = this.listner.scaleDown(this.workers);
            //boolean destroyed = true;
            this.workers = model.getWorkers(this.workload);
            //System.out.println(messageCount);
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


    private void populate() {
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


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
    LinkedBlockingQueue<PipeData> outQueue;
    int messageCount;
    int workload;
    int workers;
    ScalableContextListner listner;

    public ProductionHandler(LinkedBlockingQueue<PipeData> inQueue, LinkedBlockingQueue<PipeData> out,
                             int messageCount, int workload, int workers, ScalableContextListner listner) {
        this.inQueue = inQueue;
        this.messageCount = messageCount;
        this.workload = workload;
        this.workers = workers;
        this.listner = listner;
        this.outQueue = out;
    }

    @Override
    public void run() {


        try {
            for (int i = 0; i < messageCount; i++) {
                populate();
            }
            this.inQueue.put(new XMLMessage(-1, null, null, this.workload));
            //this.inQueue.put(new XMLMessage(-2, null, null, this.workload));
//            Thread.sleep(15000);
//            while (this.outQueue.size()!=0 && this.outQueue.size()!=0){
//                Thread.sleep(1000);
//            }
//
            boolean destroyed = this.listner.contextDestroyed(this.workers);
            //boolean destroyed = true;
            this.workers = populateModel().getWorkers(this.workload);
            System.out.println(messageCount);
            if (destroyed) {
                System.out.println("destroyed :" + destroyed);
                this.listner.contextInitialized(inQueue, outQueue, this.workers);
                for (int i = 0; i < messageCount; i++) {
                    populate();

                }
            }

            this.inQueue.put(new XMLMessage(-2, null, null, this.workload));
            this.listner.contextDestroyed(this.workers);
            System.out.println("Done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WorkLoadModel populateModel() {
        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(10, 1);//10b
        model.addWorkers(100, 1);//100b
        model.addWorkers(1000, 1);//1kb
        model.addWorkers(10000, 5);//10kb
        model.addWorkers(100000, 5);//100kb
        model.addWorkers(1000000, 5);//1mb
        model.addWorkers(10000000, 5);//0mb
        return model;
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


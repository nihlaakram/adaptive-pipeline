package lk.ac.iit.usecase.usecase01;

import lk.ac.iit.handler.TerminationHandler;
import org.apache.commons.text.RandomStringGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.concurrent.LinkedBlockingQueue;

public class NeeMain {

    public static void main(String[] args) {

        //the message size
        int messageSize = 10;

        //the no. of messages to use
        int messageCount = 10000;

        //no of stages
        int stageCount = 2;

        //contribution from each stage to the string
        int charCount = messageSize/stageCount;



        //required queues
        LinkedBlockingQueue<XMLMessage>[] queues = new LinkedBlockingQueue[stageCount+1];
        for(int i=0; i<stageCount+1; i++){
            queues[i] = new LinkedBlockingQueue<XMLMessage>();
        }

        //create the stages
        Stage [] stages = new Stage[stageCount];
        for(int i=0; i<stageCount; i++){
            stages[i] = new Stage(queues[i], queues[i+1]);
        }

        //start threads
        Thread[] threads = new Thread[stageCount];
        for(int i=0; i<stageCount; i++){
            threads[i] = new Thread(stages[i]);
            threads[i].start();
        }



        TerminationHandler term = new TerminationHandler(queues[stageCount], System.currentTimeMillis(), messageCount);
        Thread t1 = new Thread(term);
        t1.start();

        //fill data
        Producer prod = new Producer(queues[0], messageCount, messageSize, stageCount);
        Thread t2 = new Thread(prod);
        t2.start();

    }
}

class Stage implements Runnable {
    LinkedBlockingQueue<XMLMessage> queue;
    LinkedBlockingQueue<XMLMessage> queue1;


    public Stage(LinkedBlockingQueue<XMLMessage> queue, LinkedBlockingQueue<XMLMessage> queue1) {
        this.queue = queue;
        this.queue1 = queue1;

    }

    @Override
    public void run() {
        while (true) {
            try {
                XMLMessage msg = this.queue.take();
                if(msg.getTimestamp()==-1){
                    this.queue1.put(msg);

                    //terminate
                } else if(msg.getTimestamp()==-2){
                    this.queue1.put(msg);
                    break;
                    //terminate
                } else {
                    RandomStringGenerator random = new RandomStringGenerator.Builder()
                            .withinRange('0', 'z').build();
                    String charList = random.generate(msg.getWorkload());
                    msg.addToMessage(charList);
                    this.queue1.put(msg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class Producer implements Runnable {

    LinkedBlockingQueue<XMLMessage> inQueue;
    int messageCount;
    int workload;
    int workers;

    public Producer(LinkedBlockingQueue<XMLMessage> inQueue, int messageCount, int workload, int workers) {
        this.inQueue = inQueue;
        this.messageCount = messageCount;
        this.workload = workload;
        this.workers =workers;
    }

    @Override
    public void run() {

        try {
        for(int i =0; i<messageCount; i++){
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();


            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("XML_MESSAGE");
            doc.appendChild(rootElement);

                this.inQueue.put(new XMLMessage(System.currentTimeMillis(), doc, rootElement, this.workload/this.workers));

        }
            this.inQueue.put(new XMLMessage(-1, null, null, this.workload));

            for(int i =0; i<messageCount; i++){
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();


                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("XML_MESSAGE");
                doc.appendChild(rootElement);

                this.inQueue.put(new XMLMessage(System.currentTimeMillis(), doc, rootElement, 100000/this.workers));

            }
            this.inQueue.put(new XMLMessage(-2, null, null, this.workload));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}


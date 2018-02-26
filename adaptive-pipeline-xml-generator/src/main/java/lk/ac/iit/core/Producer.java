package lk.ac.iit.core;

import lk.ac.iit.data.XMLMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.concurrent.LinkedBlockingQueue;

public class Producer implements Runnable {
    private LinkedBlockingQueue<XMLMessage> inQueue;
    private int messageCount;


    public Producer(LinkedBlockingQueue<XMLMessage> inQueue, int messageCount) {
        this.inQueue = inQueue;
        this.messageCount = messageCount;
    }

    public void run() {
        try {
            for (int i = 0; i < this.messageCount; i++) {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                // root elements
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("XML_MESSAGE");
                doc.appendChild(rootElement);
                this.inQueue.put(new XMLMessage(System.currentTimeMillis(), doc, rootElement));
            }
            //terminating message
            this.inQueue.put(new XMLMessage(-1, null, null));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }
}

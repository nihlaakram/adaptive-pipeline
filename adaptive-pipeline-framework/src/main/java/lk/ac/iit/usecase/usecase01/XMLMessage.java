package lk.ac.iit.usecase.usecase01;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLMessage {


    private long timestamp;
    private Document message;
    private Element rootNode;
    private int workload;

    public XMLMessage(long timestamp, Document message, Element root, int workload) {
        this.timestamp = timestamp;
        this.message = message;
        this.rootNode = root;
        this.workload = workload;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Document getMessage() {
        return message;
    }

    public int getWorkload() {
        return this.workload;
    }

    public void addToMessage(String message) {
        Element element = this.message.createElement("RANDOM_CONTENT");
        element.appendChild(this.message.createTextNode(message));
        this.rootNode.appendChild(element);

    }


}
package lk.ac.iit.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLMessage {


    private long timestamp;
    private Document message;
    private Element rootNode;

    public XMLMessage(long timestamp, Document message, Element root) {
        this.timestamp = timestamp;
        this.message = message;
        this.rootNode = root;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Document getMessage() {
        return message;
    }

    public void addToMessage(String message) {
        Element element = this.message.createElement("RANDOM_CONTENT");
        element.appendChild(this.message.createTextNode(message));
        this.rootNode.appendChild(element);

    }


}

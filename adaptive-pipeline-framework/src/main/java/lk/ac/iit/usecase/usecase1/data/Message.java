package lk.ac.iit.usecase.usecase1.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Message {
    private Document message;
    private Element rootNode;

    public Message(Document message, Element root) {
        this.message = message;
        this.rootNode = root;
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

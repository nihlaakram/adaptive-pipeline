package lk.ac.iit.usecase;

import lk.ac.iit.core.Monitor;
import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.stage.ProducerStage;
import lk.ac.iit.stage.TerminationStage;
import org.apache.commons.text.RandomStringGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {


        int stageCount = 2;
        //int messageSize = Integer.parseInt(args[0]);
        int messageSize = 10;
        int learningThreshold = 100000;//five hundred thousand
        int maxThreads = 10;
        boolean isScale = true;
        boolean isVisualize = false;
        Monitor.initMonitor(stageCount, learningThreshold, maxThreads, isScale, isVisualize);
        Monitor monitor = Monitor.getMonitor();


        LinkedBlockingQueue<StageData> b1 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b2 = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<StageData> b3 = new LinkedBlockingQueue<>();
        //producer


        TerminationStage term = new SampleTerminationStage(b3, monitor);
        HandlerStage stage2 = new SampleHandlerStage(b2, b3, messageSize, 2);
        HandlerStage stage1 = new SampleHandlerStage(b1, b2, messageSize, 1);

        ProducerStage producer = new SampleProducer(stageCount, maxThreads, b1);


        monitor.getExecutor().addProducer(producer);
        monitor.getExecutor().addTerminator(term);
        monitor.getExecutor().addHandler(stage1, stage2);
        monitor.getExecutor().start();




    }
}

class SampleHandlerStage extends HandlerStage {

    private AtomicInteger messageSize;

    public SampleHandlerStage(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue,
                              int messagesize, int id) {
        super(inQueue, outQueue, id);
        this.messageSize = new AtomicInteger(messagesize);
    }

    public StageData onEvent(StageData data) {


        XMLMessage msg = (XMLMessage) data.getDataObject();


        RandomStringGenerator random = new RandomStringGenerator.Builder()
                .withinRange('0', 'z').build();
        String charList = random.generate(this.messageSize.get());
        msg.addToMessage(charList);
        return data;


    }


}

class SampleProducer extends ProducerStage {

    public SampleProducer(int noOfstages, int maxThreads, LinkedBlockingQueue<StageData> in) {
        super(noOfstages, maxThreads, in);

    }

    @Override
    public void addInput() {
        for (int i = 0; i < 200000; i++) {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("XML_MESSAGE");
                doc.appendChild(rootElement);
                this.getInQueue().put(new StageData(getNoOfStages(), new XMLMessage(doc, rootElement)));

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }

        }


    }
}

class SampleTerminationStage extends TerminationStage {



    public SampleTerminationStage(LinkedBlockingQueue<StageData> inQueue, Monitor monitor) {
        super(inQueue, monitor);

    }


    public void onEvent(StageData data) {
        XMLMessage msg = (XMLMessage) data.getDataObject();
    }


}

class XMLMessage {
    private Document message;
    private Element rootNode;

    public XMLMessage(Document message, Element root) {
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








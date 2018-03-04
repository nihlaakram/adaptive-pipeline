package lk.ac.iit.main;

import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.ProducerStage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.concurrent.LinkedBlockingQueue;

public class SampleProducer extends ProducerStage {

    public SampleProducer(int noOfstages, int maxThreads, LinkedBlockingQueue<StageData> in) {
        super(noOfstages, maxThreads, in);

    }

    @Override
    public void addInput() {
        for (int i = 0; i < 20000; i++) {
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

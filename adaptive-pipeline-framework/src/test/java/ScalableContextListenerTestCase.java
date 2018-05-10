import lk.ac.iit.core.executor.ScalableContextListener;
import lk.ac.iit.data.PipeData;
import lk.ac.iit.usecase.usecase01.XMLMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class ScalableContextListenerTestCase {

    private static final Logger log = Logger.getLogger(String.valueOf(ScalableContextListener.class));

    @org.junit.Test
    public void Test1() throws InterruptedException {

//        log.info("ScalableContextListener TestCase 01");
//
//        int messageSize = 100000;
//
//        //the no. of messages to use
//        int messageCount = 10;
//
//        //no of stages
//        int stageCount = 3;
//
//        //contribution from each stage to the string
//        int charCount = messageSize / stageCount;
//
//
//        //required queues
//        LinkedBlockingQueue<PipeData> in = new LinkedBlockingQueue<>();
//        LinkedBlockingQueue<PipeData> out = new LinkedBlockingQueue<>();
//
//        //create the stages
//        ScalableContextListener listner = new ScalableContextListener(in, out);
//        listner.scaleUp(stageCount);
//
//
//        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder docBuilder1 = null;
//        try {
//            docBuilder1 = docFactory.newDocumentBuilder();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }
//
//
//        Document doc = docBuilder1.newDocument();
//        Element rootElement = doc.createElement("XML_MESSAGE");
//        doc.appendChild(rootElement);
//        //producer
//        in.put(new XMLMessage(System.currentTimeMillis(), doc, rootElement, 10));
//        in.put(new XMLMessage(System.currentTimeMillis(), doc, rootElement, 10));
//        in.put(new XMLMessage(System.currentTimeMillis(), doc, rootElement, 10));
//
//        in.put(new XMLMessage(-2, doc, rootElement, 10));
//
//        out.take();
//        out.take();
//        out.take();
//        out.take();

    }

}

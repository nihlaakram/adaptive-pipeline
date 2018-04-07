package lk.ac.iit.usecase.builder.handler;

import lk.ac.iit.data.PipeData;
import lk.ac.iit.handler.ScalableWorker;
import org.apache.commons.text.RandomStringGenerator;

import java.util.concurrent.LinkedBlockingQueue;

public class XMLScalableHandler extends ScalableWorker {
    /**
     * Constructor
     *
     * @param inQueue  Input queue
     * @param outQueue Output queue
     */
    public XMLScalableHandler(LinkedBlockingQueue<PipeData> inQueue, LinkedBlockingQueue<PipeData> outQueue) {
        super(inQueue, outQueue);
    }

    @Override
    public PipeData process(PipeData data) {
        XMLMessage msg = (XMLMessage) data;
        RandomStringGenerator random = new RandomStringGenerator.Builder()
                .withinRange('0', 'z').build();
        String charList = random.generate(msg.getWorkload());
        msg.addToMessage(charList);
        return msg;
    }
}

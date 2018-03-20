package lk.ac.iit.usecase.usecase01;

import lk.ac.iit.data.PipeData;
import lk.ac.iit.handler.ScalableWorker;
import org.apache.commons.text.RandomStringGenerator;

import java.util.concurrent.LinkedBlockingQueue;

public class SampleWorker extends ScalableWorker {
    /**
     * Constructor
     * See ScalableWorker#constructor
     */
    public SampleWorker(LinkedBlockingQueue<PipeData> inQueue, LinkedBlockingQueue<PipeData> outQueue) {
        super(inQueue, outQueue);
    }

//    @Override
//    public PipeData process(PipeData data) {
//        XMLMessage msg  = (XMLMessage) data;
//        RandomStringGenerator random = new RandomStringGenerator.Builder()
//                .withinRange('0', 'z').build();
//        String charList = random.generate(msg.getWorkload());
//        msg.addToMessage(charList);
//        return msg;
//    }
}

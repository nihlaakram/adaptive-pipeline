package lk.ac.iit.main;

import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;
import org.apache.commons.text.RandomStringGenerator;

import java.util.concurrent.LinkedBlockingQueue;

public class SampleHandlerStage extends HandlerStage {

    public SampleHandlerStage(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue) {
        super(inQueue, outQueue);
    }

    public void onEvent(StageData data) {


        XMLMessage msg = (XMLMessage) data.getDataObject();


        try {
            RandomStringGenerator random = new RandomStringGenerator.Builder()
                    .withinRange('0', 'z').build();
            String charList = random.generate(100);
            msg.addToMessage(charList);
            data.setTimestamp(1);
            // System.out.println(msg.getMessage()+"\t"+charList);
            getOutQueue().put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//

    }


}

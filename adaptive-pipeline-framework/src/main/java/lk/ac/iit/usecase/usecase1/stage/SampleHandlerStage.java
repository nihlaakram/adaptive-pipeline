package lk.ac.iit.usecase.usecase1.stage;

import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;
import org.apache.commons.text.RandomStringGenerator;
import lk.ac.iit.usecase.usecase1.data.Message;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SampleHandlerStage extends HandlerStage {

    private AtomicInteger messageSize;

    public SampleHandlerStage(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue,
                              int messagesize, int id) {
        super(inQueue, outQueue, id);
        this.messageSize = new AtomicInteger(messagesize);
    }

    public StageData onEvent(StageData data) {


        Message msg = (Message) data.getDataObject();


        RandomStringGenerator random = new RandomStringGenerator.Builder()
                .withinRange('0', 'z').build();
        String charList = random.generate(this.messageSize.get());
        msg.addToMessage(charList);
        return data;



    }


}

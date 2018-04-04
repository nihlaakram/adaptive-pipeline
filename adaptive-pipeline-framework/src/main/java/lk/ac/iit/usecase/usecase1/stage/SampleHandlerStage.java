package lk.ac.iit.usecase.usecase1.stage;

import lk.ac.iit.data.StageData;
import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.usecase.usecase1.data.Message;
import org.apache.commons.text.RandomStringGenerator;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class SampleHandlerStage extends HandlerStage {

    private AtomicInteger messageSize;
    private int id;
    private int count = 0;

    public SampleHandlerStage(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue,
                              int messagesize, int id) {
        super(inQueue, outQueue, id);
        this.messageSize = new AtomicInteger(messagesize);
        this.id = id;
    }

    public StageData onEvent(StageData data) {

        //this.count++;
        System.out.println(this.id+"\t"+data.id);

        Message msg = (Message) data.getDataObject();


        RandomStringGenerator random = new RandomStringGenerator.Builder()
                .withinRange('0', 'z').build();
        String charList = random.generate(this.messageSize.get());
        msg.addToMessage(charList);
        return data;


    }


}

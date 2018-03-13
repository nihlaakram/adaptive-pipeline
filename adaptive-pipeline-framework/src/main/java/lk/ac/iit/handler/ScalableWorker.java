package lk.ac.iit.handler;

import lk.ac.iit.data.PipeData;
import lk.ac.iit.data.WorkLoadData;
import lk.ac.iit.usecase.usecase01.XMLMessage;
import org.apache.commons.text.RandomStringGenerator;

import java.util.concurrent.LinkedBlockingQueue;

public class ScalableWorker implements Runnable {

    LinkedBlockingQueue<PipeData> inQueue;
    LinkedBlockingQueue<PipeData> outQueue;

    public ScalableWorker(LinkedBlockingQueue<PipeData> inQueue, LinkedBlockingQueue<PipeData> outQueue) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;

    }

    @Override
    public void run() {
        while (true) {
            try {
                XMLMessage msg = (XMLMessage) this.inQueue.take();
                if (msg.getTimestamp() == WorkLoadData.scale() || msg.getTimestamp() == WorkLoadData.termination()) {
                    this.outQueue.put(msg);
                    break;

                } else {
                    RandomStringGenerator random = new RandomStringGenerator.Builder()
                            .withinRange('0', 'z').build();
                    String charList = random.generate(msg.getWorkload());
                    msg.addToMessage(charList);
                    this.outQueue.put(msg);

                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
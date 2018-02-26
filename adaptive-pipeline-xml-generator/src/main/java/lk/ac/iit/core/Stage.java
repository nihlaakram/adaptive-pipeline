package lk.ac.iit.core;

import lk.ac.iit.data.XMLMessage;
import org.apache.commons.text.RandomStringGenerator;

import java.util.concurrent.LinkedBlockingQueue;

public class Stage implements Runnable {


    private LinkedBlockingQueue<XMLMessage> inQueue;
    private LinkedBlockingQueue<XMLMessage> outQueue;
    private int charCount;


    public Stage(LinkedBlockingQueue<XMLMessage> inQueue, LinkedBlockingQueue<XMLMessage> outQueue, int charCount) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
        this.charCount = charCount;
    }

    public void run() {
        while (true) {


            //check queue size
            if (this.inQueue.size() > 0) {

                //take item from inQueue
                XMLMessage msg = this.inQueue.poll();

                try {
                    //check if last element
                    if (msg.getTimestamp() == -1) {
                        this.outQueue.put(msg);
                        break;
                    } else {
                        // generate and add content
                        RandomStringGenerator random = new RandomStringGenerator.Builder()
                                .withinRange('0', 'z').build();
                        String charList = random.generate(this.charCount);
                        msg.addToMessage(charList);

                        //push it to outQueue
                        this.outQueue.put(msg);


                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }


    }

}

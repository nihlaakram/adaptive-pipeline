package lk.ac.iit.main;

import lk.ac.iit.data.StageData;
import lk.ac.iit.data.HandlerStage;
import org.apache.commons.text.RandomStringGenerator;

import java.util.concurrent.LinkedBlockingQueue;

public class SampleHandlerStage extends HandlerStage {

    public SampleHandlerStage(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue) {
        super(inQueue, outQueue);
    }

    public void process() {

                StageData val1 = getInQueue().poll();



                    if (!val1.getTerminate()) {
                        XMLmessage msg = (XMLmessage) val1.getDataObject();


                        try {
                            RandomStringGenerator random = new RandomStringGenerator.Builder()
                                    .withinRange('0', 'z').build();
                            String charList = random.generate(100);
                            msg.addToMessage(charList);
                            val1.setTimestamp(1);
                            // System.out.println(msg.getMessage()+"\t"+charList);
                            getOutQueue().put(val1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            for (int i = 0; i < 5; i++) {
                                //getOutQueue().put(new TerminationMessage());
                                StageData data = new StageData(-1, null);
                                data.setTerminate();
                                this.getOutQueue().put(data);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
//

        }





}

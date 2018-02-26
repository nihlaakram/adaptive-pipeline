package lk.ac.iit.core;

import lk.ac.iit.data.disruptor.handler.FinalHandler;
import lk.ac.iit.data.disruptor.handler.IntermediateHandler;

public class Executor {

    int count = 0;
    private FinalHandler[] finalHandlers;

    public Executor(int noOfHandlers) {
        finalHandlers = new FinalHandler[noOfHandlers];
    }

    public void addHandler(FinalHandler... handlers) {
        this.finalHandlers = handlers;
    }

    public void executeScaling(int stageID) {
//        FinalHandler stageHandler = this.finalHandlers[stageID - 1].clone();
//        Thread t1 = new Thread(stageHandler);
//        t1.start();

       // finalHandlers[count].setNum(count + 1);

        count++;
        IntermediateHandler.setNum(2);

    }


}

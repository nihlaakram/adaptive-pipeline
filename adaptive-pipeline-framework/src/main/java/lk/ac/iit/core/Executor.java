package lk.ac.iit.core;

import lk.ac.iit.data.disruptor.handler.FinalStageHandler;
import lk.ac.iit.data.disruptor.handler.IntermediateStageHandler;

public class Executor {

    int count = 0;
    private FinalStageHandler[] finalStageHandlers;

    public Executor(int noOfHandlers) {
        finalStageHandlers = new FinalStageHandler[noOfHandlers];
    }

    public void addHandler(FinalStageHandler... handlers) {
        this.finalStageHandlers = handlers;
    }

    public void executeScaling(int stageID) {
//        FinalStageHandler stageHandler = this.finalStageHandlers[stageID - 1].clone();
//        Thread t1 = new Thread(stageHandler);
//        t1.start();

        // finalStageHandlers[count].setNum(count + 1);

        count++;
        if (count <= 2) {

            if (stageID == 1) {
                IntermediateStageHandler.setNum(2);
                System.out.println("Scaling1");
            } else {
//                FinalStageHandler.setNum(2);
//                System.out.println("Scaling2");
            }
        }


        //
        //
        //StageHandler.setNum(2);

    }


}

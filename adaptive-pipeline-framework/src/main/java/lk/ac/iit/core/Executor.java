package lk.ac.iit.core;

import lk.ac.iit.data.StageEvent;
import lk.ac.iit.data.disruptor.handler.FinalStageHandler;
import lk.ac.iit.data.disruptor.handler.IntermediateStageHandler;

public class Executor {

    private int noOfHandlers;
    private IntermediateStageHandler[] stageHandlers;
    private FinalStageHandler[] finalStageHandler;

    private Class[] classes;
    public Executor(int noOfHandlers) {
       this.noOfHandlers = noOfHandlers;
    }

    public void addIntermediateHandler(IntermediateStageHandler ... handlers) {
       this.stageHandlers = handlers;
    }

    public void addFinalHandler(FinalStageHandler ... handler) {
        this.finalStageHandler = handler;
    }

    public void addHandler(Class clazz) {
    }


    public void executeScaling(int stageID) {
        System.out.println("Scaling "+noOfHandlers);
//        if(stageID == this.noOfHandlers)(
//                this.finalStageHandler[0].
//                )

//        count++;
//        if (count <= 2) {
//
//            if (stageID == 1) {
//                IntermediateStageHandler.setNum(2);
//                System.out.println("Scaling1");
//            } else {
////                FinalStageHandler.setNum(2);
////                System.out.println("Scaling2");
//            }
//        }

    }


}

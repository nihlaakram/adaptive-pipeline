package lk.ac.iit.core;

import lk.ac.iit.data.StageHandler;

public class Executor {

    private StageHandler[] stageHandlers;

    public Executor(int noOfHandlers) {
        stageHandlers = new StageHandler[noOfHandlers];
    }

    public void addHandler(StageHandler... handlers) {
        this.stageHandlers = handlers;
    }

    public void executeScaling(int stageID) {
        System.out.println("Hello");
        StageHandler stageHandler = this.stageHandlers[stageID - 1].clone();
        Thread t1 = new Thread(stageHandler);
        t1.start();
    }


}
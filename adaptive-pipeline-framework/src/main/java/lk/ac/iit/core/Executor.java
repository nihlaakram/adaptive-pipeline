package lk.ac.iit.core;

import lk.ac.iit.core.planner.Planner;

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
        StageHandler stageHandler = this.stageHandlers[stageID - 1].clone();
        Thread t1 = new Thread(stageHandler);
        t1.start();
    }


}
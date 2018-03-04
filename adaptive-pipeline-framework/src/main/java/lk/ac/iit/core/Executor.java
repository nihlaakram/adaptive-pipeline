package lk.ac.iit.core;

import lk.ac.iit.stage.HandlerStage;

public class Executor {

    private HandlerStage[] handlerStages;

    public Executor(int noOfHandlers) {
        handlerStages = new HandlerStage[noOfHandlers];
    }

    public void addHandler(HandlerStage... handlers) {
        this.handlerStages = handlers;
    }

    public void executeScaling(int stageID) {
        HandlerStage handlerStage = this.handlerStages[stageID - 1].clone();
        Thread t1 = new Thread(handlerStage);
        t1.start();

    }


}
package lk.ac.iit.core;

import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.stage.ProducerStage;

public class Executor {

    private HandlerStage[] handlerStages;
    private ProducerStage producerStage;

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


    public void addProducer(ProducerStage producer) {
        this.producerStage = producer;
    }

    public void start(){
        //Start producer
        this.producerStage.start();

        //Start the handlers
        for(int i=0; i<this.handlerStages.length; i++){
            Thread thread = new Thread(this.handlerStages[i], "handler-thread-"+(i+1));
            thread.start();
        }
    }
}
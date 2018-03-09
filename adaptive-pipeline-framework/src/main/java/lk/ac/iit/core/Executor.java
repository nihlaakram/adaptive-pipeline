package lk.ac.iit.core;

import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.stage.ProducerStage;
import lk.ac.iit.stage.TerminationStage;

public class Executor {

    private HandlerStage[] handlerStages;
    private ProducerStage producerStage;
    private TerminationStage terminationStage;

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
        System.out.println("Scaling Stage :" + stageID);


    }


    public void addProducer(ProducerStage producer) {
        this.producerStage = producer;
    }

    public void start() {
        //Start producer
        this.producerStage.start();

        //startTerminator
        Thread term = new Thread(this.terminationStage, "termination-thread");
        term.start();

        //Start the handlers
        for (int i = 0; i < this.handlerStages.length; i++) {
            Thread thread = new Thread(this.handlerStages[i], "handler-thread-" + (i + 1));
            thread.start();
        }
    }

    public void addTerminator(TerminationStage term) {
        this.terminationStage = term;
    }
}
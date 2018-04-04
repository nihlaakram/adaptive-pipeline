package lk.ac.iit.core.executor;

import lk.ac.iit.stage.HandlerStage;
import lk.ac.iit.stage.ProducerStage;
import lk.ac.iit.stage.TerminationStage;

public class Executor {

    private HandlerStage[] handlerStages;
    private ProducerStage producerStage;
    private TerminationStage terminationStage;

    /**
     * Adding the stages of the pipeline
     * @param handlers
     */
    public void addHandler(HandlerStage... handlers) {

        handlerStages = new HandlerStage[handlers.length];
        this.handlerStages = handlers;
    }

    /**
     * Executes scaling based on the stage ID
     * @param stageID The ID of the stage to be scaled
     */
    public void executeScaling(int stageID) {


        HandlerStage handlerStage = this.handlerStages[stageID - 1].clone();
        Thread t1 = new Thread(handlerStage);
        t1.start();
        System.out.println("Scaling Stage :" + stageID);


    }


    /**
     * Adds the producer stage
     * @param producer
     */
    public void addProducer(ProducerStage producer) {
        this.producerStage = producer;
    }

    /**
     * Starts execution of the framework
     */
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

    /**
     * Adds the termination stage
     * @param term
     */
    public void addTerminator(TerminationStage term) {
        this.terminationStage = term;
    }
}
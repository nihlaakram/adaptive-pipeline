package lk.ac.iit.core;

import lk.ac.iit.data.StageHandler;

public class Executor {

    private StageHandler[] stageHandlers;
    private int count = 0;

    public Executor(int noOfHandlers) {
        stageHandlers = new StageHandler[noOfHandlers];
    }

    public void addHandler(StageHandler handler) {
        this.stageHandlers[count] = handler;
        count++;

    }

    public void executeScaling(int stageID) {
        try {
            System.out.println("Scaled1");
            StageHandler stageHandler = this.stageHandlers[stageID - 1].clone();
            System.out.println("Scaled2");
            Thread t1 = new Thread(stageHandler);
            System.out.println("Scaled3");
            t1.start();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }


}

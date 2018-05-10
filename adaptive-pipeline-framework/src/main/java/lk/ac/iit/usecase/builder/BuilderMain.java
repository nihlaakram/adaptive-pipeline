package lk.ac.iit.usecase.builder;

import lk.ac.iit.core.analyser.workload.WorkLoadModel;
import lk.ac.iit.handler.JPipeScaler;
import lk.ac.iit.usecase.builder.handler.XMLScalableHandler;

public class BuilderMain {

    public static void main(String[] args) {

        //the message size
        int messageSize = 100000;

        //the no. of messages to use
        int messageCount = 10000;

        //no of stages
        int stageCount = 1;

//        the message size
//        int messageSize = Integer.parseInt(args[1]);
//
//        //the no. of messages to use
//        int messageCount = Integer.parseInt(args[2]);
//
//        //no of stages
//        int stageCount = Integer.parseInt(args[0]);

        JPipeScaler scaler = new JPipeScaler(populateModel(), true, messageSize, messageCount, stageCount);
        scaler.addWorker(XMLScalableHandler.class);
        scaler.start();

    }

    public static WorkLoadModel populateModel() {
        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(-1, 10, 1);//10b
        model.addWorkers(-1, 100, 1);//100b
        model.addWorkers(-1, 1000, 1);//1kb
        model.addWorkers(-1, 10000, 5);//10kb
        model.addWorkers(-1, 100000, 5);//100kb
        model.addWorkers(-1, 1000000, 5);//1mb
        model.addWorkers(-1, 10000000, 5);//0mb

        model.addWorkers(1000, 10, 1);//10b
        model.addWorkers(1000, 100, 1);//100b
        model.addWorkers(1000, 1000, 1);//1kb
        model.addWorkers(1000, 10000, 2);//10kb
        model.addWorkers(1000, 100000, 3);//100kb
        model.addWorkers(1000, 1000000, 5);//1mb
        model.addWorkers(1000, 10000000, 4);//0mb

        model.addWorkers(100, 10, 1);//10b
        model.addWorkers(100, 100, 1);//100b
        model.addWorkers(100, 1000, 1);//1kb
        model.addWorkers(100, 10000, 2);//10kb
        model.addWorkers(100, 100000, 3);//100kb
        model.addWorkers(100, 1000000, 3);//1mb
        model.addWorkers(100, 10000000, 4);//0mb

        model.addWorkers(10, 10, 1);//10b
        model.addWorkers(10, 100, 1);//100b
        model.addWorkers(10, 1000, 1);//1kb
        model.addWorkers(10, 10000, 2);//10kb
        model.addWorkers(10, 100000, 3);//100kb
        model.addWorkers(10, 1000000, 3);//1mb
        model.addWorkers(10, 10000000, 4);//0mb

        model.addWorkers(1, 10, 1);//10b
        model.addWorkers(1, 100, 1);//100b
        model.addWorkers(1, 1000, 1);//1kb
        model.addWorkers(1, 10000, 1);//10kb
        model.addWorkers(1, 100000, 2);//100kb
        model.addWorkers(1, 1000000, 3);//1mb
        model.addWorkers(1, 10000000, 3);//0mb

        return model;
    }

}

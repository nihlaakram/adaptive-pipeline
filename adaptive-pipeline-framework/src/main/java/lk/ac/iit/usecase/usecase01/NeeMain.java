package lk.ac.iit.usecase.usecase01;

import lk.ac.iit.core.analyser.workload.WorkLoadModel;
import lk.ac.iit.data.PipeData;
import lk.ac.iit.handler.JPipeScaler;
import lk.ac.iit.handler.PerformanceHandler;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;

import java.util.concurrent.LinkedBlockingQueue;

public class NeeMain {

    public static void main(String[] args) {


        BasicConfigurator.configure(new ConsoleAppender(new PatternLayout(" [%t] %p - %m%n")));
//        //the message size
        int messageSize = 10000;

        //the no. of messages to use
        int messageCount = 10;

        //no of stages
        int stageCount = 1;

        //the message size
//        int messageSize = Integer.parseInt(args[1]);
//
//        //the no. of messages to use
//        int messageCount = Integer.parseInt(args[2]);
//
//        //no of stages
//        int stageCount = Integer.parseInt(args[0]);


        JPipeScaler scaler = new JPipeScaler(populateModel(), true, messageSize, messageCount, stageCount);

        scaler.start();

    }

    public static WorkLoadModel populateModel() {
        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(10, 1);//10b
        model.addWorkers(100, 1);//100b
        model.addWorkers(1000, 1);//1kb
        model.addWorkers(10000, 5);//10kb
        model.addWorkers(100000, 5);//100kb
        model.addWorkers(1000000, 5);//1mb
        model.addWorkers(10000000, 5);//0mb
        return model;
    }
}

package lk.ac.iit.usecase.usecase01;

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
//        int messageSize = 10;
//
//        //the no. of messages to use
//        int messageCount = 100000;
//
//        //no of stages
//        int stageCount = 5;

        //the message size
        int messageSize = 100000;

        //the no. of messages to use
        int messageCount = 10000;

        //no of stages
        int stageCount = 2;


        JPipeScaler scaler = new JPipeScaler(null, true, messageSize, messageCount, stageCount);

        //required queues
        LinkedBlockingQueue<PipeData> out = scaler.getOutputQueue();

        //create the stages
        //ScalableContextListener listner = new ScalableContextListener();
        // listner.contextInitialized(in, out, stageCount);


        PerformanceHandler term = new PerformanceHandler(out, System.currentTimeMillis());
        Thread t1 = new Thread(term, "termination");
        t1.start();

        //fill data
        // ProductionHandler prod = new ProductionHandler(in, out, messageCount, messageSize, stageCount, listner);
        //scaler.startProduction(prod);


        scaler.start();

    }
}

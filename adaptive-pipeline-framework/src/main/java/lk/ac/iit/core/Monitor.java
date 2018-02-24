package lk.ac.iit.core;


import lk.ac.iit.core.analyser.Analyser;
import lk.ac.iit.core.analyser.learner.SiddhiLearner;

//
public class Monitor {

    private static Monitor monitor1;
    private Analyser analyser;
    private int monitorThreshold;
    private int noOfStage;

    private Executor executor;


    public Monitor(int noOfStage, int monitorThreshold) {
        //StaticBlockSingleton
        this.analyser = new Analyser(noOfStage, monitorThreshold);
        this.monitorThreshold = monitorThreshold;
        this.noOfStage = noOfStage;
        this.executor = new Executor(noOfStage);

    }

    //access monitor1 functions through this
    public synchronized static Monitor getMonitor1() {

        return monitor1;
    }

    public static void initMonitor(int stageCount, int monitorCount) {
        if (monitor1 == null) {
            //lazy thread safe
            monitor1 = new Monitor(stageCount, monitorCount);
        }
    }


    //receive timestamp related data
    public synchronized void setTimestamp(long[] timestamp) {

//        if (tempCount == monitorThreshold) {
//
//            try {
//                if (this.tempCount == this.monitorThreshold) {
//                    System.out.println("Temp Count : " + this.tempCount);
//                    sendDataToAnalyser();
//                    resetMonitor();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        this.timeArray[this.tempCount] = timestamp;
//        this.tempCount++;

        //send data to siddhi
        analyser.analyse(timestamp);

        //get the result from


    }

    public Executor getExecutor() {
        return this.executor;
    }
}

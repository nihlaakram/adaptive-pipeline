package lk.ac.iit.core;


import lk.ac.iit.core.analyser.Analyser;
import lk.ac.iit.core.analyser.AnalyserData;
import lk.ac.iit.core.planner.Planner;
import lk.ac.iit.core.planner.PlannerData;

//
public class Monitor {

    private static Monitor monitor1;
    private Analyser analyser;
    private int monitorThreshold;
    private int noOfStage;
    private boolean terminated;

    private long[][] timeArray;
    private int tempCount = 0;


    public Monitor( int noOfStage, int monitorThreshold) {
        //StaticBlockSingleton
        this.analyser = new Analyser(noOfStage, monitorThreshold);
        this.terminated = false;
        this.monitorThreshold = monitorThreshold;
        this.noOfStage = noOfStage;
        this.timeArray = new long[monitorThreshold][noOfStage];

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


    private synchronized void sendDataToAnalyser() {
        AnalyserData analyserData = this.analyser.analyse(timeArray);
        System.out.println("hello");
        //calculate
//        Planner planner = new Planner(analyserData, 5);
//        PlannerData plannerData = planner.plan();
//        System.out.println(plannerData.isScalability()+"\t"+plannerData.isScalability());

    }

    private synchronized void resetMonitor() {
        this.timeArray = new long[monitorThreshold][noOfStage+1];
        this.tempCount = 0;
        notifyAll();
    }

    public void setTerminated() {
        this.terminated = true;
    }

    //receive timestamp related data
    public synchronized void setTimestamp(long[] timestamp) {

//            for(int i = 0; i<this.noOfStage; i++){
//                if(timestamp[i]<0l){
//                    throw new NegativeTimestampException(timestamp[i]);
//                } else {
//                    this.timeArray[i].add(timestamp[i]);
//                }
//
//            }
        if (tempCount == monitorThreshold) {

            try {
                System.out.println("waiting");
                if (this.tempCount == this.monitorThreshold) {
//                notifyAll();
                    System.out.println("Temp Count : " + this.tempCount);
                    sendDataToAnalyser();
                    resetMonitor();
                }
                System.out.println("waiting released");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.timeArray[this.tempCount] = timestamp;
        this.tempCount++;


    }
}

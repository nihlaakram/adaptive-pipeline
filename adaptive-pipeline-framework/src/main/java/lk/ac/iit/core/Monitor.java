package lk.ac.iit.core;


import lk.ac.iit.core.analyser.Analyser;
import lk.ac.iit.core.analyser.AnalyserData;
import lk.ac.iit.core.planner.Planner;

//
public class Monitor implements Runnable {

    private static Monitor monitor1;
    private Analyser analyser;
    private int monitorThreshold;
    private int noOfStage;
    private boolean terminated;

    private long[][] timeArray;
    private int tempCount = 0;


    public Monitor(Planner planner, int noOfStage, int monitorThreshold) {
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

    public static void initMonitor(Planner planner, int stageCount, int monitorCount) {
        if (monitor1 == null) {
            //lazy thread safe
            monitor1 = new Monitor(planner, stageCount, monitorCount);
        }
    }

    @Override
    public void run() {

        while (!this.terminated) {

            if (this.tempCount == this.monitorThreshold) {
                System.out.println("Temp Count : " + this.tempCount);
                sendDataToAnalyser();
                resetMonitor();
            }


        }

    }

    private synchronized void sendDataToAnalyser() {
        AnalyserData analyserData = this.analyser.analyse(timeArray);
        //calculate
        System.out.println("Avg Lat "+analyserData.getAvgLatency()[0]+"\t"+analyserData.getAvgLatency()[1]+"\t");
        System.out.println("TPS "+analyserData.getTpsArr()[0]+"\t"+analyserData.getTpsArr()[1]+"\t");

    }

    private synchronized void resetMonitor() {
        this.timeArray = new long[monitorThreshold][noOfStage];
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
        if (tempCount == 1000) {

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.timeArray[this.tempCount] = timestamp;
        this.tempCount++;


    }
}

package lk.ac.iit.core;


//
public class Monitor implements Runnable {

    private static Monitor monitor1;
    private Analyzer analyzer;
    private int monitorThreshold;
    private int noOfStage;
    private boolean terminated;

    private long[][] timeArray;
    private int tempCount = 0;


    public Monitor(Executor executor, int noOfStage, int monitorThreshold) {
        //StaticBlockSingleton
        this.analyzer = new Analyzer(executor, noOfStage, monitorThreshold);
        this.terminated = false;
        this.monitorThreshold = monitorThreshold;
        this.noOfStage = noOfStage;
        this.timeArray = new long[monitorThreshold][noOfStage];

    }

    //access monitor1 functions through this
    public synchronized static Monitor getMonitor1() {
        return monitor1;
    }

    public static void initMonitor(Executor executor, int stageCount, int monitorCount) {
        if (monitor1 == null) {
            //lazy thread safe
            monitor1 = new Monitor(executor, stageCount, monitorCount);
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
        this.analyzer.analyser(timeArray);
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

package lk.ac.iit.core;


//
public class Monitor implements Runnable{

    private Analyzer analyzer;
    private static Monitor monitor1;

    private int monitorThreshold;
    private int noOfStage;
    private boolean terminated;

    private long [][] timeArray;
    private int tempCount =0;



    public Monitor(Analyzer analyzer, int noOfStage, int monitorThreshold){
        //StaticBlockSingleton
        this.analyzer = analyzer;
        this.terminated = false;
        this.monitorThreshold = monitorThreshold;
        this.noOfStage = noOfStage;
        this.timeArray = new long[monitorThreshold][noOfStage];

    }

    //access monitor1 functions through this
    public synchronized static Monitor getMonitor1(){
        return monitor1;
    }

    @Override
    public void run() {

        while (!this.terminated){

                if(this.tempCount==this.monitorThreshold) {
                    System.out.println("Temp Count : " + this.tempCount);
                    sendDataToAnalyser();
                    resetMonitor();
                }





        }

    }

    private synchronized void sendDataToAnalyser(){
        for(int i = 0; i<this.monitorThreshold; i++){
            //System.out.print(i+" ");
            for(int j = 0; j<this.noOfStage; j++){
                System.out.print(this.timeArray[i][j]+"\t");
            }
            System.out.println();

        }
    }

    private synchronized void resetMonitor(){
        this.timeArray = new long[monitorThreshold][noOfStage];
        this.tempCount = 0;
        notifyAll();
    }

    public void setTerminated(){
        this.terminated = true;
    }

    public static void initMonitor(Analyzer analyzer, int stageCount, int monitorCount){
        if(monitor1 == null){
            //lazy thread safe
            monitor1 = new Monitor(analyzer, stageCount, monitorCount);
        }
    }

    //receive timestamp related data
    public synchronized void setTimestamp(long [] timestamp) {

//            for(int i = 0; i<this.noOfStage; i++){
//                if(timestamp[i]<0l){
//                    throw new NegativeTimestampException(timestamp[i]);
//                } else {
//                    this.timeArray[i].add(timestamp[i]);
//                }
//
//            }
        if(tempCount==1000){

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

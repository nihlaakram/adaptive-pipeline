package lk.ac.iit.core;


import lk.ac.iit.execption.NegativeTimestampException;

import java.util.ArrayList;

//
public class Monitor implements Runnable{

    private Analyzer analyzer;
    private static Monitor monitor;

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

    //access monitor functions through this
    public synchronized static Monitor getMonitor(){
        return monitor;
    }

    @Override
    public void run() {

        while (!this.terminated){
            //if count is reached, send data to analyser
            if(this.tempCount==this.monitorThreshold){
                for(int i = 0; i<this.monitorThreshold; i++){
                    //System.out.print(i+" ");
                    for(int j = 0; j<this.noOfStage; j++){
                        System.out.print(this.timeArray[i][j]+"\t");
                    }
                    System.out.println();

                }
                break;
            }


        }

    }

    public void setTerminated(){
        this.terminated = true;
    }

    public static void initMonitor(Analyzer analyzer, int stageCount, int monitorCount){
        if(monitor == null){
            //lazy thread safe
            monitor = new Monitor(analyzer, stageCount, monitorCount);
        }
    }

    //receive timestamp related data
    public void setTimestamp(long [] timestamp) throws NegativeTimestampException{

//            for(int i = 0; i<this.noOfStage; i++){
//                if(timestamp[i]<0l){
//                    throw new NegativeTimestampException(timestamp[i]);
//                } else {
//                    this.timeArray[i].add(timestamp[i]);
//                }
//
//            }
        this.timeArray[this.tempCount] = timestamp;
        this.tempCount++;


    }
}

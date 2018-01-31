package lk.ac.iit.core;


import java.util.ArrayList;

//
public class Monitor implements Runnable{

    private Analyzer analyzer;
    private static Monitor monitor;

    private int monitorThreshold;
    private int noOfStage;
    private boolean terminated;

    private ArrayList<Long> [] latencyArr;



    public Monitor(Analyzer analyzer, int noOfStage, int monitorThreshold){
        //StaticBlockSingleton
        this.analyzer = analyzer;
        this.terminated = false;
        this.monitorThreshold = monitorThreshold;
        this.noOfStage = noOfStage;
        this.latencyArr = new ArrayList[this.noOfStage];

        for(int i = 0; i<this.noOfStage; i++){
            this.latencyArr[i] = new ArrayList<>();
        }

    }

    //access monitor functions through this
    public synchronized static Monitor getMonitor(){
        return monitor;
    }

    @Override
    public void run() {

        while (!this.terminated){
            //if count is reached, send data to analyser
            if(this.latencyArr[this.noOfStage -1].size()==this.monitorThreshold){
                for(int i = 0; i<this.monitorThreshold; i++){
                    System.out.print(i+" ");
                    for(int j = 0; j<this.noOfStage; j++){
                        System.out.print(this.latencyArr[j].get(i)+"\t");
                    }
                    System.out.println();

                }
               // break;
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

    //sending latency data
    public void setLatency(long [] latency){
        for(int i = 0; i<this.noOfStage; i++){
            this.latencyArr[i].add(latency[i]);
        }
    }
}

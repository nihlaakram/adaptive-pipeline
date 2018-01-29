package lk.ac.iit.core;


import java.util.ArrayList;

//
public class Monitor implements Runnable{

    private Analyzer analyzer;
    private static Monitor monitor;

    private int monitorCount;
    private int stageCount;
    private boolean terminated;

    private ArrayList<Long> [] latencyArr;



    public Monitor(int stageCount, int monitorCount){
        //StaticBlockSingleton
        this.analyzer = new Analyzer();
        this.terminated = false;
        this.monitorCount = monitorCount;
        this.stageCount = stageCount;
        this.latencyArr = new ArrayList[this.stageCount];

        for(int i=0; i<this.stageCount; i++){
            this.latencyArr[i] = new ArrayList<>();
        }

    }

    //access monitor functions through this
    public synchronized static Monitor getMonitor(){
        //System.out.println("Requested for monitor"+monitor);
        return monitor;
    }

    @Override
    public void run() {

        while (!this.terminated){
            //System.out.println("Thread running"+latencyArr[0]);
            //if count is reached, send data to analyser
            if(this.latencyArr[this.stageCount-1].size()==this.monitorCount){
                for(int i=0; i<this.monitorCount; i++){
                    System.out.print(i+" ");
                    for(int j=0; j<this.stageCount; j++){
                        System.out.print(this.latencyArr[j].get(i)+"\t");
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

    public static void initMonitor(int stageCount, int monitorCount){
        if(monitor == null){
            //lazy thread safe
            monitor = new Monitor(stageCount, monitorCount);
           // System.out.println("No monitor found, creates one"+monitor);
        }
        //return monitor;
    }

    //sending latency data
    public void setLatency(long [] latency){
        for(int i=0; i<this.stageCount; i++){
            this.latencyArr[i].add(latency[i]);
        }
        //System.out.println(this.latencyArr[0].get(0));

    }
}

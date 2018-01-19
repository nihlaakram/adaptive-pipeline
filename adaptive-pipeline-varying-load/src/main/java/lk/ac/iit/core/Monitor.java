package lk.ac.iit.core;



//
public class Monitor implements Runnable{

    private Analyzer analyzer;
    private static Monitor monitor;

    private int monitorCount;
    private boolean terminated;



    public Monitor(int stageCount, int monitorCount){
        //StaticBlockSingleton
        this.analyzer = new Analyzer();
        this.terminated = false;
        this.monitorCount = monitorCount;
    }

    //access monitor functions through this
    public synchronized static Monitor getMonitor(){
        System.out.println("Requested for monitor"+monitor);
        return monitor;
    }

    @Override
    public void run() {

        while (!this.terminated){}

    }

    public void setTerminated(){
        this.terminated = true;
    }

    public static void initMonitor(int stageCount, int monitorCount){
        if(monitor == null){

            monitor = new Monitor(stageCount, monitorCount);
            System.out.println("No monitor found, creates one"+monitor);
        }
        //return monitor;
    }

    //sending latency data
    public void setLatency(long [] latency){
        int length = latency.length;
        for (int i=0; i<length; i++)
        System.out.print(latency[i]+"\t");

    }
}

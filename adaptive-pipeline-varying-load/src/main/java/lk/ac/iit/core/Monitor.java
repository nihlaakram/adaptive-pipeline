package lk.ac.iit.core;



//
public class Monitor implements Runnable{

    private Analyzer analyzer;
    private static Monitor monitor;

    private int monitorCount;
    private boolean terminated;



    private Monitor(int monitorCount){
        this.analyzer = new Analyzer();
        this.terminated = false;
        this.monitorCount = monitorCount;
    }

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

    public static void initMonitor(int monitorCount){
        if(monitor == null){

            monitor = new Monitor(monitorCount);
            System.out.println("No monitor fount, creates one"+monitor);
        }
        //return monitor;
    }
}

package lk.ac.iit.core;



//
public class Monitor implements Runnable{

    private Analyzer analyzer;
    private static Monitor monitor;

    private int monitorCount;
    private boolean terminated;



    private Monitor(){
        this.analyzer = new Analyzer();
        this.terminated = false;
    }

    public synchronized static Monitor getMonitor(){
        if(monitor == null){
            monitor = new Monitor();
        }
        return monitor;
    }

    @Override
    public void run() {

        while ()

    }
}

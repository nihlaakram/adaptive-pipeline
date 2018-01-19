package lk.ac.iit.core;



//
public class Monitor {

    private Analyzer analyzer;
    private static Monitor monitor;

    private Monitor(){
        this.analyzer = new Analyzer();
    }

    public synchronized static Monitor getMonitor(){
        if(monitor == null){
            monitor = new Monitor();
        }
        return monitor;
    }
}

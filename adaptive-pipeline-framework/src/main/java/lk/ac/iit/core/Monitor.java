package lk.ac.iit.core;


import lk.ac.iit.core.analyser.Analyser;

//
public class Monitor {

    private static Monitor monitor;
    private Analyser analyser;
    private Executor executor;


    public Monitor(int noOfStage, int monitorThreshold) {
        //StaticBlockSingleton
        this.analyser = new Analyser(noOfStage, monitorThreshold);
        this.executor = new Executor(noOfStage);

    }

    //access monitor functions through this
    public synchronized static Monitor getMonitor() {
        return monitor;
    }

    public static void initMonitor(int stageCount, int monitorCount) {
        if (monitor == null) {//lazy thread safe
            monitor = new Monitor(stageCount, monitorCount);
        }
    }


    //receive timestamp related data
    public synchronized void setTimestamp(long... timestamp) {
        analyser.analyse(timestamp);
    }

    public Executor getExecutor() {
        return this.executor;
    }

}

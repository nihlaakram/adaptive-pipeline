package lk.ac.iit.core;


import lk.ac.iit.core.analyser.Analyser;
import lk.ac.iit.core.planner.Planner;

//
public class Monitor {

    private static Monitor monitor;
    private Analyser analyser;
    private Executor executor;
    private Planner planner;


    public Monitor(int noOfStage, int learningThreshold, int maxThreads, boolean isScale, boolean isVisualize) {
        //StaticBlockSingleton
        this.planner = new Planner(maxThreads);
        this.analyser = new Analyser(noOfStage, learningThreshold, this.planner, isScale);
        this.executor = new Executor(noOfStage);

    }

    public Monitor(int noOfStage, int learningThreshold, boolean isScale, boolean isVisualize) {
        //StaticBlockSingleton
        this.planner = new Planner();
        this.analyser = new Analyser(noOfStage, learningThreshold, this.planner, isScale);
        this.executor = new Executor(noOfStage);

    }

    //access monitor functions through this
    public synchronized static Monitor getMonitor() {
        return monitor;
    }

    public static void initMonitor(int stageCount, int learningThreshold, int maxThreads, boolean isScale,
                                   boolean isVisalize) {
        if (monitor == null) {//lazy thread safe
            monitor = new Monitor(stageCount, learningThreshold, maxThreads, isScale, isVisalize);
        }
    }

    public static void initMonitor(int stageCount, int learningThreshold, boolean isScale, boolean isVisualize) {
        if (monitor == null) {//lazy thread safe
            monitor = new Monitor(stageCount, learningThreshold, isScale, isVisualize);
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

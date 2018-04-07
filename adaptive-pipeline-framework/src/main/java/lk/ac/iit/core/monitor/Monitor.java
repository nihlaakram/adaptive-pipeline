package lk.ac.iit.core.monitor;


import lk.ac.iit.core.analyser.Analyser;
import lk.ac.iit.core.executor.Executor;
import lk.ac.iit.core.planner.Planner;

//
public class Monitor {

    private static Monitor monitor;
    private Analyser analyser;
    private Executor executor;
    private Planner planner;


    /**
     * Constructor : Creates a Monitor object
     *
     * @param noOfStage
     * @param learningThreshold
     * @param maxThreads
     * @param isScale
     */
    private Monitor(int noOfStage, int learningThreshold, int maxThreads, boolean isScale) {
        //StaticBlockSingleton
        this.planner = new Planner(maxThreads);
        this.analyser = new Analyser(noOfStage, learningThreshold, this.planner, isScale);
        this.executor = new Executor();

    }

    /**
     * Constructor (overloaded): Creates a Monitor object
     *
     * @param noOfStage
     * @param learningThreshold
     * @param isScale
     */
    private Monitor(int noOfStage, int learningThreshold, boolean isScale) {
        //StaticBlockSingleton
        this.planner = new Planner();
        this.analyser = new Analyser(noOfStage, learningThreshold, this.planner, isScale);
        this.executor = new Executor();

    }

    /**
     * Accessing the monitor
     *
     * @return
     */
    public synchronized static Monitor getMonitor() {
        //access monitor functions through this
        return monitor;
    }

    /**
     * Instantiating the monitor
     *
     * @param stageCount
     * @param learningThreshold
     * @param maxThreads
     * @param isScale
     */
    public static void initMonitor(int stageCount, int learningThreshold, int maxThreads, boolean isScale) {
        if (monitor == null) {//lazy thread safe
            monitor = new Monitor(stageCount, learningThreshold, maxThreads, isScale);
        }
    }

    /**
     * nstantiating the monitor overloaded
     *
     * @param stageCount
     * @param learningThreshold
     * @param isScale
     */
    public static void initMonitor(int stageCount, int learningThreshold, boolean isScale) {
        if (monitor == null) {//lazy thread safe
            monitor = new Monitor(stageCount, learningThreshold, isScale);
        }
    }


    /**
     * Receive timestamp related data
     *
     * @param timestamp
     */
    public synchronized void setTimestamp(long... timestamp) {
        analyser.analyse(timestamp);
    }

    /**
     * Accessing the executor
     *
     * @return
     */
    public Executor getExecutor() {
        return this.executor;
    }
}

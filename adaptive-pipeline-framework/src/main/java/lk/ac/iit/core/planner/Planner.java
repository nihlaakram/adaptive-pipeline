package lk.ac.iit.core.planner;

import lk.ac.iit.core.analyser.AnalyserData;

public class Planner {


    private AnalyserData data;
    private final int MAX_THREADS;
    private int noOfThread;

    public Planner(AnalyserData data, int maxThread) {
        this.data = data;
        MAX_THREADS = maxThread;
        this.noOfThread = Thread.activeCount()-1;
    }

    public Planner(AnalyserData data) {
        this.data = data;
        MAX_THREADS = Runtime.getRuntime().availableProcessors() * 2;
        this.noOfThread = Thread.activeCount()-1;
    }


    public AnalyserData getData() {
        return data;
    }



    public int getMAX_THREADS() {
        return MAX_THREADS;
    }

    public int getNoOfThread() {
        return noOfThread;
    }




}

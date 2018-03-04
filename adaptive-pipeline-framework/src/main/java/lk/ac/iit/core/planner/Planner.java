package lk.ac.iit.core.planner;

import lk.ac.iit.core.analyser.AnalyserData;

public class Planner {


    private final int MAX_THREADS;
    private int noOfThread;

    public Planner(int maxThread) {
        MAX_THREADS = maxThread;
        this.noOfThread = Thread.activeCount() - 1;
    }

    public Planner() {
        MAX_THREADS = Runtime.getRuntime().availableProcessors() * 2;
        this.noOfThread = Thread.activeCount() -1;
    }


    public int getMAX_THREADS() {
        return MAX_THREADS;
    }

    public int getNoOfThread() {
        return this.noOfThread = Thread.activeCount()-1;
    }

    //check is the system can scale
    private boolean systemScale() {
        if (getMAX_THREADS() > getNoOfThread()) {
            return true;
        }
        return false;
    }

    //check latency and see if scaling is required
    private PlannerData latencyScale(AnalyserData data) {
        PlannerData plannerData = new PlannerData(true, getMax(data.getAvgLatency()));
        //System.out.println(this.data.getAvgLatency()[0]);
        return plannerData;
    }

    //check tps and see if scaling is required
    private PlannerData tpsScale(AnalyserData data) {
        PlannerData plannerData = new PlannerData(true, getMin(data.getAvgLatency()));
        return plannerData;
    }


    private int getMax(double[] inputArray) {
        double maxValue = inputArray[0];
        int maxIndex = 0;
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
                maxIndex = i;
            }
        }
        return maxIndex + 1;
    }

    private int getMin(double[] inputArray) {
        double maxValue = inputArray[0];
        int maxIndex = 0;
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] < maxValue) {
                maxValue = inputArray[i];
                maxIndex = i;
            }
        }
        return maxIndex + 1;
    }

    public PlannerData plan(AnalyserData analyserData) {
        //check if the system is capable of scaling
        if (this.systemScale()) {
            //check latency
            PlannerData data = this.latencyScale(analyserData);
            if (data.isScalability()) {
                return data;
            } else {
                //return tps
                return this.tpsScale(analyserData);
            }
        }
        return new PlannerData(false, -1);
    }
}
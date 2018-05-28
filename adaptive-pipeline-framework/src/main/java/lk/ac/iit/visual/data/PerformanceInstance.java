package lk.ac.iit.visual.data;

public class PerformanceInstance {

    private int stageID;
    private int stageCount;
    private double[] perfNumbers = new double[2];

    public PerformanceInstance(int noOfStages, int stageCount, double latency, double throughput) {
        this.stageID = noOfStages;
        this.stageCount = stageCount;
        this.perfNumbers[0] = latency;
        this.perfNumbers[1] = throughput;

    }

    public int getStageID() {
        return this.stageID;
    }

    public int getStageCount() {
        return this.stageCount;
    }

    public double[] getPerfNumbers() {
        return this.perfNumbers;
    }


}

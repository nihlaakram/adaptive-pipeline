package lk.ac.iit.core;

public class Analyzer {

    private int noOfStages;
    private int monitorThreshold;

    private int [] latencyArr;
    private int [] tpsArr;

    public Analyzer(int noOfStages,int monitorThreshold){
        this.noOfStages = noOfStages;
        this.monitorThreshold = monitorThreshold;
    }
}

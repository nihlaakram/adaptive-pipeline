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

    //check is the system can scale
    private boolean systemScale(){
        if(getMAX_THREADS()>getNoOfThread()){
            return true;
        }
        return  false;
    }

    //check latency and see if scaling is required
    private PlannerData latencyScale(){
        PlannerData plannerData = new PlannerData(true, getMax(this.data.getAvgLatency()));
        //System.out.println(this.data.getAvgLatency()[0]);
        return plannerData;
    }

    //check tps and see if scaling is required
    private PlannerData tpsScale(){
        PlannerData plannerData = new PlannerData(true, getMin(this.data.getAvgLatency()));
        return plannerData;
    }


    private int getMax(double[] inputArray){
        double maxValue = inputArray[0];
        int maxIndex = 0;
        for(int i=1;i < inputArray.length;i++){
            if(inputArray[i] > maxValue){
                maxValue = inputArray[i];
                maxIndex = i;
            }
        }
        return maxIndex+1;
    }
    private int getMin(double[] inputArray){
        double maxValue = inputArray[0];
        int maxIndex = 0;
        for(int i=1;i < inputArray.length;i++){
            if(inputArray[i] < maxValue){
                maxValue = inputArray[i];
                maxIndex = i;
            }
        }
        return maxIndex+1;
    }

    public PlannerData plan() {
        //check if the system is capable of scaling
        if(this.systemScale()){
            //check latency
            PlannerData data = this.latencyScale();
            if(data.isScalability()){
                return data;
            } else {
                //return tps
                return this.tpsScale();
            }
        }
        return new PlannerData(false, -1);
    }
}

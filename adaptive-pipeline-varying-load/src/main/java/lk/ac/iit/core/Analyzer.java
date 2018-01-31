package lk.ac.iit.core;

public class Analyzer {

    private int noOfStages;
    private int noOfSummarizer;
    private int monitorThreshold;
    private Executor executor;

    private long[] latencyArr;
    private double[] tpsArr;
    long [] startTime;
    long [] endTime;
    double [] avgLatency;

    public Analyzer(Executor executor, int noOfStages, int monitorThreshold) {
        this.executor = executor;
        this.noOfStages = noOfStages;
        this.monitorThreshold = monitorThreshold;
        this.noOfSummarizer = getNoOfStages() -1;

        //initPerfSummarizer();

    }

    private void initPerfSummarizer(){
        this.latencyArr = new long [noOfSummarizer];
        this.tpsArr = new double [noOfSummarizer];
        this.startTime = new long [getNoOfStages()];
        this.endTime = new long [getNoOfStages()];
        this.avgLatency = new double[getNoOfStages()-1];
    }

    public void analyser(long[][] timeData) {
        initPerfSummarizer();
        //startTime and endTime related data
        this.startTime = timeData[0];
        this.endTime = timeData[getMonitorThreshold()-1];

        //calculate TPS


        //calculate total latency & average latency
        for (int j = 0; j < this.noOfStages-1; j++) {
            for (int i = 0; i < this.monitorThreshold; i++) {
                this.latencyArr[j] = latencyArr[j]+(timeData[i][j+1]-timeData[i][j]);

            }
            avgLatency[j] = (latencyArr[j]/getMonitorThreshold()) ;
            tpsArr[j] = getMonitorThreshold()/(endTime[j+1]-startTime[j]);
        }

        //calculate
        System.out.println("Avg Lat "+avgLatency[0]+"\t"+avgLatency[1]+"\t");
        System.out.println("TPS "+tpsArr[0]+"\t"+tpsArr[1]+"\t");



    }



    public int getNoOfStages() {
        return this.noOfStages;
    }

    public int getMonitorThreshold() {
        return this.monitorThreshold;
    }

    public Executor getExecutor() {
        return this.executor;
    }

}

package lk.ac.iit.core;

public class Analyzer {

    private int noOfStages;
    private int noOfSummerizer;
    private int monitorThreshold;
    private Executor executor;

    private long[] latencyArr;
    private int[] tpsArr;
    long [] startTime;
    long [] endTime;

    public Analyzer(Executor executor, int noOfStages, int monitorThreshold) {
        this.executor = executor;
        this.noOfStages = noOfStages;
        this.monitorThreshold = monitorThreshold;
        this.noOfSummerizer = getNoOfStages() -1;

        //initPerfSummarizer();

    }

    private void initPerfSummarizer(){
        this.latencyArr = new long [noOfSummerizer];
        this.tpsArr = new int [noOfSummerizer];
        this.startTime = new long [getNoOfStages()];
        this.endTime = new long [getNoOfStages()];
    }

    public void analyser(long[][] timeData) {
        initPerfSummarizer();
        //startData
        this.startTime = timeData[0];
        System.out.println(startTime[0]+"\t"+startTime[1]+"\t"+startTime[2]+"\t");


        for (int j = 0; j < this.noOfStages-1; j++) {
            for (int i = 0; i < this.monitorThreshold; i++) {
                this.latencyArr[j] = latencyArr[j]+(timeData[i][j+1]-timeData[i][j]);
                //System.out.println(timeData[i][j]);

            }
        }

        this.endTime = timeData[getMonitorThreshold()-1];
        System.out.println(endTime[0]+"\t"+endTime[1]+"\t"+endTime[2]+"\t");

        System.out.println(latencyArr[0]+"\t"+latencyArr[1]+"\t");
        double [] avgLatency = new double[getNoOfStages()-1];
        for(int i=0; i<noOfSummerizer; i++){
            avgLatency[i] = (latencyArr[i]/noOfStages) ;
        }
        System.out.println(avgLatency[0]+"\t"+avgLatency[1]+"\t");




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

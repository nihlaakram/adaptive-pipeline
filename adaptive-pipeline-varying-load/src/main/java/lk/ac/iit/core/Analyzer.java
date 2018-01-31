package lk.ac.iit.core;

public class Analyzer {

    private int noOfStages;
    private int monitorThreshold;

    private int[] latencyArr;
    private int[] tpsArr;

    public Analyzer(Executor executor, int noOfStages, int monitorThreshold) {
        this.noOfStages = noOfStages;
        this.monitorThreshold = monitorThreshold;
    }


    public void analyser(long[][] timeData) {
        for (int i = 0; i < this.monitorThreshold; i++) {
            for (int j = 0; j < this.noOfStages; j++) {
                System.out.print(timeData[i][j] + "\t");
            }
            System.out.println();

        }
    }
}

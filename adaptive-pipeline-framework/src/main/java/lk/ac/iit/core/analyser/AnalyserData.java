package lk.ac.iit.core.analyser;

public class AnalyserData {


    private double[] tpsArr;
    private double[] avgLatency;

    /**
     * Constructor : Creates an instance of the analysed data
     * @param tpsArr
     * @param avgLatency
     */
    public AnalyserData(double[] tpsArr, double[] avgLatency) {
        this.tpsArr = tpsArr;
        this.avgLatency = avgLatency;
    }


    /**
     * Returns the tps values of each stage
     * @return
     */
    public double[] getTpsArr() {
        return tpsArr;
    }


    /**
     * Returns the average latency values of each stage
     * @return
     */
    public double[] getAvgLatency() {
        return avgLatency;
    }
}
package lk.ac.iit.core.analyser;
public class AnalyserData {


    private double[] tpsArr;
    private double[] avgLatency;

    public AnalyserData(double[] tpsArr, double[] avgLatency) {
        this.tpsArr = tpsArr;
        this.avgLatency = avgLatency;
    }

    public double[] getTpsArr() {
        return tpsArr;
    }

    public double[] getAvgLatency() {
        return avgLatency;
    }
}
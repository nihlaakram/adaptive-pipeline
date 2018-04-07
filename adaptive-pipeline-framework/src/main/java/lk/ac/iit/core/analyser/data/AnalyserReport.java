package lk.ac.iit.core.analyser.data;

public class AnalyserReport {



    private double avgLatency;
    private double tps;


    public AnalyserReport(double avgLatency, double tps) {
        this.avgLatency = avgLatency;
        this.tps = tps;
    }


    public double getAvgLatency() {
        return avgLatency;
    }

    public double getTps() {
        return tps;
    }


}

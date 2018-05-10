package lk.ac.iit.core.analyser.data;

public class AnalyserReport {


    private double avgLatency;
    private double tps;


    /**
     * Constructor
     * @param avgLatency : average latency of the pipeline
     * @param tps : throughput of the pipeline
     */
    public AnalyserReport(double avgLatency, double tps) {
        this.avgLatency = avgLatency;
        this.tps = tps;
    }


    /**
     *
     * @return average latency of the pipeline
     */
    public double getAvgLatency() {
        return avgLatency;
    }

    /**
     *
     * @return throughput of the pipeline
     */
    public double getTps() {
        return tps;
    }


}

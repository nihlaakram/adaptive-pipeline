package lk.ac.iit.core.analyser.data;

public class AnalyserReport {


    private AnalyserData analyserData;
    private AnalysedData analysedData;


    public AnalyserReport(AnalyserData analyserData, AnalysedData analysedData) {
        this.analyserData = analyserData;
        this.analysedData = analysedData;
    }

    public AnalyserData getAnalyserData() {
        return this.analyserData;
    }

    public AnalysedData getAnalysedData() {
        return this.analysedData;
    }


}

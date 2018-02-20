package lk.ac.iit.core.analyser;

import lk.ac.iit.core.analyser.learner.SiddhiLearner;
import org.wso2.siddhi.core.event.Event;

public class Analyser {

    SiddhiLearner siddhi = new SiddhiLearner();
    private int noOfStages;
    private int noOfSummarizer;
    private int monitorThreshold;
    private long[] latencyArr;
    private double[] tpsArr;
    private long[] startTime;
    private long[] endTime;
    private double[] avgLatency;

    public Analyser(int noOfStages, int monitorThreshold) {

        this.noOfStages = noOfStages;
        this.monitorThreshold = monitorThreshold;
        this.noOfSummarizer = getNoOfStages();

        //initPerfSummarizer();

    }

    private void initPerfSummarizer() {
        this.latencyArr = new long[noOfSummarizer];
        this.tpsArr = new double[noOfSummarizer];
        this.startTime = new long[getNoOfStages()];
        this.endTime = new long[getNoOfStages()];
        this.avgLatency = new double[getNoOfStages()];
    }

    public void analyse(long[] timestamp) {
//        initPerfSummarizer();
//        //startTime and endTime related data
//        this.startTime = timeData[0];
//        this.endTime = timeData[getMonitorThreshold()-1];
//
//        System.out.println(noOfStages);
//        //calculate TPS & average latency
//        for (int j = 0; j < this.noOfStages; j++) {
//            for (int i = 0; i < this.monitorThreshold; i++) {
//                this.latencyArr[j] = latencyArr[j]+(timeData[i][j+1]-timeData[i][j]);
//
//            }
//            avgLatency[j] = (latencyArr[j]/getMonitorThreshold()) ;
//            tpsArr[j] = getMonitorThreshold()/(endTime[j+1]-startTime[j]);
//        }
        siddhi.publish(new Event(System.currentTimeMillis(), new Object[]{timestamp[1] - timestamp[0], timestamp[2] - timestamp[1]}));


//        AnalyserData analyzerData = new AnalyserData(this.tpsArr, this.avgLatency);
//        System.out.println("TPS : "+this.tpsArr[0]+"\t"+this.tpsArr[1]);
//        System.out.println("Avg Lat: "+this.avgLatency[0]+"\t"+this.avgLatency[1]);
//        return analyzerData;


    }


    public int getNoOfStages() {
        return this.noOfStages;
    }

    public int getMonitorThreshold() {
        return this.monitorThreshold;
    }


}

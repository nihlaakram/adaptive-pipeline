package lk.ac.iit.core.analyser;

import lk.ac.iit.core.analyser.learner.SiddhiLearner;
import org.wso2.siddhi.core.event.Event;

public class Analyser {

    private SiddhiLearner siddhi;
    private int noOfStages;
    private int noOfSummarizer;
    private int monitorThreshold;

    public Analyser(int noOfStages, int monitorThreshold) {

        this.noOfStages = noOfStages;
        this.monitorThreshold = monitorThreshold;
        this.noOfSummarizer = getNoOfStages();
        this.siddhi = new SiddhiLearner(this.monitorThreshold);

        //initPerfSummarizer();

    }


    public void analyse(long[] timestamp) {

        siddhi.publish(new Event(System.currentTimeMillis(), new Object[]{timestamp[1] - timestamp[0], timestamp[2] - timestamp[1]}));

    }


    public int getNoOfStages() {
        return this.noOfStages;
    }


}
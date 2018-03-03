package lk.ac.iit.core.analyser;

import lk.ac.iit.core.analyser.learner.query.SiddhiLearner;
import lk.ac.iit.core.planner.Planner;
import org.wso2.siddhi.core.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Analyser {

    private SiddhiLearner siddhi;
    private int monitorThreshold;
    private int objLength;
    private Planner planner;

    public Analyser(int noOfStages, int monitorThreshold, Planner planner, boolean isScale) {
        this.monitorThreshold = monitorThreshold;
        this.objLength = 2 * noOfStages;
        this.planner = planner;
        this.siddhi = new SiddhiLearner(this.monitorThreshold, this.objLength, this.planner, isScale);

    }


    public void analyse(long... timestamp) {

        Object[] latencyPreprocessed = new Object[this.objLength / 2];
        Object[] tpsPreprocessed = new Object[this.objLength / 2];

        //preprocessed attributes for latency and tps
        for (int i = 0; i < this.objLength / 2; i++) {
            latencyPreprocessed[i] = timestamp[i + 1] - timestamp[i];
            tpsPreprocessed[i] = timestamp[i];
        }
        List list = new ArrayList(Arrays.asList(latencyPreprocessed));
        list.addAll(Arrays.asList(tpsPreprocessed));
        Object[] obj = list.toArray();

        siddhi.publish(new Event(System.currentTimeMillis(), obj));

    }


}
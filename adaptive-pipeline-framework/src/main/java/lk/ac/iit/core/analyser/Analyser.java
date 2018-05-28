package lk.ac.iit.core.analyser;

import lk.ac.iit.core.analyser.siddhi.query.SiddhiAnalyser;
import lk.ac.iit.core.planner.Planner;
import org.wso2.siddhi.core.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Copyright 2018, Nihla Akram
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */


public class Analyser {

    private SiddhiAnalyser siddhi;
    private int monitorThreshold;
    private int objLength;
    private Planner planner;


    /**
     * Constructor : creates the instance of the analyser
     *
     * @param noOfStages       the number of stages in the pipeline
     * @param monitorThreshold the number of events to be monitored
     * @param planner          the planning component of the framework
     * @param isScale          should the system scale
     */
    public Analyser(int noOfStages, int monitorThreshold, Planner planner, boolean isScale) {
        this.monitorThreshold = monitorThreshold;
        this.objLength = 2 * noOfStages;
        this.planner = planner;
        this.siddhi = new SiddhiAnalyser(this.monitorThreshold, this.objLength, this.planner, isScale);

    }


    /**
     * Records the time spent in stage and sends it to Siddhi for latency and tps calculations
     *
     * @param timestamp the record of time spent in each stage
     */
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
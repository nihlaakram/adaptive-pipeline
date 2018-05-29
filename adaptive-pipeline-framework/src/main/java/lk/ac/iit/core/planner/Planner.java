/**
 * Copyright 2018, Nihla Akram
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package lk.ac.iit.core.planner;

import lk.ac.iit.core.analyser.data.AnalysedData;
import lk.ac.iit.core.analyser.data.AnalyserData;

public class Planner {


    private final int MAX_THREADS;
    private int noOfThread;

    public Planner(int maxThread) {
        MAX_THREADS = maxThread;
        this.noOfThread = Thread.activeCount() - 1;
    }

    public Planner() {
        MAX_THREADS = Runtime.getRuntime().availableProcessors() * 2;
        this.noOfThread = Thread.activeCount() - 1;
    }


    public int getMAX_THREADS() {
        return MAX_THREADS;
    }

    public int getNoOfThread() {
        this.noOfThread = Thread.activeCount() - 1;
        return this.noOfThread;
    }

    //check is the system can scale
    private boolean systemScale() {
        if (getMAX_THREADS() > getNoOfThread()) {
            return true;
        }
        return false;
    }

    //check latency and see if scaling is required
    private PlannerData latencyScale(AnalyserData data) {
        PlannerData plannerData = new PlannerData(true, getMax(data.getAvgLatency()));
        //System.out.println(this.data.getAvgLatency()[0]);
        return plannerData;
    }

    //check tps and see if scaling is required
    private PlannerData tpsScale(AnalyserData data) {
        PlannerData plannerData = new PlannerData(true, getMin(data.getAvgLatency()));
        return plannerData;
    }


    private int getMax(double[] inputArray) {
        double maxValue = inputArray[0];
        int maxIndex = 0;
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] > maxValue) {
                maxValue = inputArray[i];
                maxIndex = i;
            }
        }
        return maxIndex + 1;
    }

    private int getMin(double[] inputArray) {
        double maxValue = inputArray[0];
        int maxIndex = 0;
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i] < maxValue) {
                maxValue = inputArray[i];
                maxIndex = i;
            }
        }
        return maxIndex + 1;
    }

    public PlannerData plan(AnalyserData analyserData) {
        //check if the system is capable of scaling
        if (this.systemScale()) {
            //check latency
            PlannerData data = this.latencyScale(analyserData);
            if (data.isScalability()) {
                return data;
            } else {
                //return tps
                return this.tpsScale(analyserData);
            }
        }
        return new PlannerData(false, -1);
    }

    public PlannerData plan(AnalysedData analysedData) {

        //check if the system is capable of scaling
        if (this.systemScale()) {
            PlannerData data = new PlannerData(analysedData.isScalability(), analysedData.getHandlerID());
            return data;

        }
        return new PlannerData(false, -1);
    }
}
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

package lk.ac.iit.core.analyser;

import lk.ac.iit.core.analyser.data.AnalysedData;
import lk.ac.iit.core.analyser.data.AnalyserData;

public class GreedyOptimizer {

    private double[] tpsArr;
    private double[] latArr;
    private int handlerID;

    public GreedyOptimizer(AnalyserData data) {
        this.tpsArr = data.getTpsArr();
        this.latArr = data.getAvgLatency();
        this.handlerID = -1;
    }

    public AnalysedData analyse() {
        //optimized performance is proportional to throughput
        int result1 = analyseTps(this.tpsArr, 0);

        //optimized performance is inversely proportional to throughput
        int result2 = analyseLatency(this.latArr, 0);

        //optimized performance
        if (result1 == result2) {
            this.handlerID = result2 + 1;
            return new AnalysedData(this.handlerID, true);
        } else {
            int result3 = analyseRatios(this.tpsArr, this.latArr, 0);
            this.handlerID = result3 + 1;
            return new AnalysedData(this.handlerID, true);
        }

    }


    private int analyseRatios(double[] tpsArr, double[] latArr, int count) {

        if (count == tpsArr.length - 1 && count == latArr.length - 1) {
            return count;
        } else {
            int index = analyseRatios(tpsArr, latArr, count + 1);
            if ((tpsArr[count] / latArr[count]) < (tpsArr[index] / latArr[index])) {
                return count;
            } else {
                return index;
            }
        }
    }

    private int analyseTps(double[] tpsArr, int count) {
        if (count == tpsArr.length - 1) {
            return count;
        } else {
            int index = analyseTps(tpsArr, count + 1);
            if (tpsArr[count] < tpsArr[index]) {
                return count;
            } else {
                return index;
            }
        }

    }

    private int analyseLatency(double[] latArr, int count) {
        if (count == latArr.length - 1) {
            return count;
        } else {
            int index = analyseTps(latArr, count + 1);
            if (latArr[count] > latArr[index]) {
                return count;
            } else {
                return index;
            }
        }

    }

}




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

package lk.ac.iit.core.analyser.data;

public class AnalyserData {


    private double[] tpsArr;
    private double[] avgLatency;

    /**
     * Constructor : Creates an instance of the analysed data
     *
     * @param tpsArr
     * @param avgLatency
     */
    public AnalyserData(double[] tpsArr, double[] avgLatency) {
        this.tpsArr = tpsArr;
        this.avgLatency = avgLatency;
    }


    /**
     * Returns the tps values of each stage
     *
     * @return
     */
    public double[] getTpsArr() {
        return tpsArr;
    }


    /**
     * Returns the average latency values of each stage
     *
     * @return
     */
    public double[] getAvgLatency() {
        return avgLatency;
    }
}
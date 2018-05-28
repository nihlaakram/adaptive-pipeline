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

package lk.ac.iit.core.analyser.workload;

public class WorkLoadKey {

    private int inputRate;
    private int workLoadParam;

    /**
     * The key to access the WorkLoadModel
     *
     * @param inputRate     the rate at which input is pumped in to the worker/workers
     * @param workLoadParam the workload parameter of the worker/workers
     */
    public WorkLoadKey(int inputRate, int workLoadParam) {
        this.inputRate = inputRate;
        this.workLoadParam = workLoadParam;
    }

    /**
     * See Object#equals
     *
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
        if (obj instanceof WorkLoadKey) {
            WorkLoadKey key = (WorkLoadKey) obj;
            return ((this.inputRate == key.inputRate) && (this.workLoadParam == key.workLoadParam));
        }
        return false;

    }


    /**
     * See Object#hashCode
     *
     * @return
     */
    public int hashCode() {
        return (31 * this.inputRate) ^ this.workLoadParam;
    }
}

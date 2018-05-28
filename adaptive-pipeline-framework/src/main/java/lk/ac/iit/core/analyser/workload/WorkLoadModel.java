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

import java.util.HashMap;
import java.util.Map;

public class WorkLoadModel implements BaseModel {

    private Map<WorkLoadKey, Integer> pastResults;

    /**
     * Creates a WorkLoadModel with past data
     */
    public WorkLoadModel() {
        this.pastResults = new HashMap<>();
    }

    /**
     * Returns the results of the Workload model
     *
     * @return past results of the model
     */
    public Map<WorkLoadKey, Integer> getPastResults() {
        return this.pastResults;
    }

    /**
     * See BaseModel#getWorkers
     */
    @Override
    public int getWorkers(int inputRate, int workLoadParam) {
        return this.pastResults.get(new WorkLoadKey(inputRate, workLoadParam));
    }

    /**
     * See BaseModel#getWorkers
     */
    @Override
    public int getWorkers(int workLoadParam) {
        return this.pastResults.get(new WorkLoadKey(1, workLoadParam));
    }

    /**
     * See BaseModel#setModel
     */
    @Override
    public void setModel(WorkLoadModel baseModel) {
        this.pastResults = baseModel.getPastResults();
    }

    /**
     * See BaseModel#addWorkers
     */
    @Override
    public void addWorkers(int workLoadParam, int workers) {
        this.pastResults.put(new WorkLoadKey(1, workLoadParam), workers);
    }

    /**
     * See BaseModel#addWorkers
     */
    @Override
    public void addWorkers(int inputRate, int workLoadParam, int workers) {
        this.pastResults.put(new WorkLoadKey(inputRate, workLoadParam), workers);
    }
}

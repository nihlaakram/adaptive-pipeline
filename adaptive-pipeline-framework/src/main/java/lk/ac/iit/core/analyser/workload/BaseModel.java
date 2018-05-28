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

public interface BaseModel {

    /**
     * Returns the number of recommended workers for a give input rate and workload parameter
     *
     * @param inputRate     the rate at which input is pumped in to the worker/workers
     * @param workLoadParam the workload parameter of the worker/workers
     * @return the recommended number of workers
     */
    int getWorkers(int inputRate, int workLoadParam);

    /**
     * Returns the number of recommended workers for a give input rate and workload parameter
     *
     * @param workLoadParam the workload parameter of the worker/workers
     * @return the recommended number of workers
     */
    int getWorkers(int workLoadParam);


    /**
     * Add past experiment results to the model
     *
     * @param workLoadParam the workload parameter of the worker/workers
     * @param workers       the number of recommended workers
     */
    void addWorkers(int workLoadParam, int workers);

    /**
     * Set the Workload model
     *
     * @param baseModel the workload model
     */
    void setModel(WorkLoadModel baseModel);

    /**
     * Add past experiment results to the model
     *
     * @param inputRate     the rate at which input is pumped in to the worker/workers
     * @param workLoadParam the workload parameter of the worker/workers
     * @param workers       the number of recommended workers
     */
    void addWorkers(int inputRate, int workLoadParam, int workers);


}

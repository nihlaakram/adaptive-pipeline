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

package lk.ac.iit.stage;

import lk.ac.iit.data.StageData;
import lk.ac.iit.data.TerminationMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerStage extends Thread {
    private BlockingQueue<StageData> inQueue;
    private int maxThreads;
    private int noOfStages;

    public ProducerStage(int noOfStages, int maxThreads, LinkedBlockingQueue<StageData> in) {
        this.inQueue = in;
        this.maxThreads = maxThreads;
        this.noOfStages = noOfStages;
    }

    @Override
    public void run() {
        addInput();
        addTermination();
    }

    private void addTermination() {
        try {
            for (int i = 0; i < this.maxThreads; i++) {
                StageData data = new TerminationMessage();
                this.inQueue.put(data);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addInput() {
    }

    public BlockingQueue<StageData> getInQueue() {
        return this.inQueue;
    }

    public int getNoOfStages() {
        return this.noOfStages;
    }
}

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

public class HandlerStage implements Cloneable, Runnable {


    private BlockingQueue<StageData> inQueue;
    private BlockingQueue<StageData> outQueue;
    private int stageID;


    public HandlerStage(LinkedBlockingQueue<StageData> inQueue, LinkedBlockingQueue<StageData> outQueue, int stageID) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
        this.stageID = stageID;
    }


    public BlockingQueue<StageData> getInQueue() {
        return inQueue;
    }

    public BlockingQueue<StageData> getOutQueue() {
        return outQueue;
    }


    @Override
    public void run() {
        while (true) {
            if (getInQueue().size() > 0) {
                try {
                    StageData data = getInQueue().poll();
                    if (!data.getTerminate()) {
                        data = onEvent(data);
                        data.setTimestamp(this.stageID);
                        getOutQueue().put(data);
                    } else {
                        terminate();
                        break;
                    }
                } catch (NullPointerException e) {
                    //e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void terminate() {
        try {
            StageData data = new TerminationMessage();
            this.outQueue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public StageData onEvent(StageData data) {
        return null;
    }

    public HandlerStage clone() {

        HandlerStage t = null;
        try {
            t = (HandlerStage) super.clone();
        } catch (CloneNotSupportedException e) {


        }

        return t;
    }

}

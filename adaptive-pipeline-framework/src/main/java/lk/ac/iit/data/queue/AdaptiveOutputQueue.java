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

package lk.ac.iit.data.queue;

import lk.ac.iit.data.PipeData;

import java.util.concurrent.LinkedBlockingQueue;

public class AdaptiveOutputQueue {

    private static LinkedBlockingQueue<PipeData> outQueue;
    private static AdaptiveOutputQueue outputQueue;


    /**
     * Constructor
     * <p>
     * Creates the output queue with maximum size
     */
    private AdaptiveOutputQueue() {
        this.outQueue = new LinkedBlockingQueue();
    }

    /**
     * Constructor
     * Creates the output queue with given size
     *
     * @param size the size of the queue
     */
    private AdaptiveOutputQueue(int size) {
        this.outQueue = new LinkedBlockingQueue(size);
    }

    /**
     * Creates a new instance of the queue or returns the existing instance for a given size.
     *
     * @param size the size of the queue
     * @return the queue
     */
    public synchronized static LinkedBlockingQueue<PipeData> getOutputQueue(int size) {
        if (outputQueue == null) {
            outputQueue = new AdaptiveOutputQueue(size);
        }
        return outQueue;
    }

    /**
     * Creates a new instance of the queue or returns the existing instance.
     *
     * @return the queue
     */
    public synchronized static LinkedBlockingQueue<PipeData> getOutputQueue() {
        if (outputQueue == null) {
            outputQueue = new AdaptiveOutputQueue();
        }
        return outQueue;
    }

}

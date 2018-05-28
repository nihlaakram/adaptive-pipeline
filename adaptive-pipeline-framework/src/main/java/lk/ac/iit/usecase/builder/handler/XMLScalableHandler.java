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

package lk.ac.iit.usecase.builder.handler;

import lk.ac.iit.data.PipeData;
import lk.ac.iit.handler.ScalableWorker;
import org.apache.commons.text.RandomStringGenerator;

import java.util.concurrent.LinkedBlockingQueue;

public class XMLScalableHandler extends ScalableWorker {
    /**
     * Constructor
     *
     * @param inQueue  Input queue
     * @param outQueue Output queue
     */
    public XMLScalableHandler(LinkedBlockingQueue<PipeData> inQueue, LinkedBlockingQueue<PipeData> outQueue) {
        super(inQueue, outQueue);
    }

    @Override
    public PipeData process(PipeData data) {
        XMLMessage msg = (XMLMessage) data;
        RandomStringGenerator random = new RandomStringGenerator.Builder()
                .withinRange('0', 'z').build();
        String charList = random.generate(msg.getWorkload());
        msg.addToMessage(charList);
        return msg;
    }
}

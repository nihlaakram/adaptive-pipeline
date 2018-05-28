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
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLMessage implements PipeData {


    private long timestamp;
    private Document message;
    private Element rootNode;
    private int workload;

    public XMLMessage(long timestamp, Document message, Element root, int workload) {
        this.timestamp = timestamp;
        this.message = message;
        this.rootNode = root;
        this.workload = workload;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }


    public Document getMessage() {
        return message;
    }

    @Override
    public int getWorkload() {
        return this.workload;
    }

    public void addToMessage(String message) {
        Element element = this.message.createElement("RANDOM_CONTENT");
        element.appendChild(this.message.createTextNode(message));
        this.rootNode.appendChild(element);

    }
}

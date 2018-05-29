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

package lk.ac.iit.data;

public class StageData implements Data {

    public int id;
    private long[] timestampArr;
    private Object data;
    private boolean isTerminate = false;

    public StageData(int noOfStages, Object data, int id) {
        this.timestampArr = new long[noOfStages + 1];
        if (noOfStages > 0) {
            this.timestampArr[0] = System.currentTimeMillis();
        }

        this.data = data;
        this.id = id;
    }

    @Override
    public long[] getTimestamp() {
        return this.timestampArr;
    }

    @Override
    public void setTimestamp(int stageId) {
        this.timestampArr[stageId] = System.currentTimeMillis();
    }

    @Override
    public Object getDataObject() {
        return this.data;
    }

    @Override
    public void setDataObject(Object dataObject) {
        this.data = dataObject;
    }

    public void setTerminate() {
        this.isTerminate = true;
    }

    public boolean getTerminate() {
        return this.isTerminate;
    }
}

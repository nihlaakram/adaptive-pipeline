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

package lk.ac.iit.visual.data;

import java.util.ArrayList;

public class PerformanceLogger {

    private static PerformanceLogger performanceLogger;
    private ArrayList<PerformanceInstance> before = new ArrayList<>();
    private ArrayList<PerformanceInstance> after = new ArrayList<>();

    public synchronized static PerformanceLogger getLogger() {
        if (performanceLogger == null) {
            performanceLogger = new PerformanceLogger();
        }
        return performanceLogger;
    }

    public void addAfter(PerformanceInstance instance) {
        this.after.add(instance);
    }

    public void addBefore(PerformanceInstance instance) {
        this.before.add(instance);
    }

    public ArrayList<PerformanceInstance> getAfter() {
        return this.after;
    }

    public ArrayList<PerformanceInstance> getBefore() {
        return this.before;
    }

}

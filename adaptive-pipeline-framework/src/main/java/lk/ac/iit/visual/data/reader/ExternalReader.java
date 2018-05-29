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

package lk.ac.iit.visual.data.reader;

import lk.ac.iit.visual.data.PerformanceInstance;
import lk.ac.iit.visual.data.PerformanceLogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExternalReader {


    private PerformanceLogger performanceLogger = new PerformanceLogger();
    private int scalableStage;

    private ExternalReader(PerformanceLogger performanceLogger, int scalableStage) {
        this.performanceLogger = performanceLogger;
        this.scalableStage = scalableStage;

    }

    public ExternalReader() {

    }


    public PerformanceLogger getPerformanceLogger() {
        return this.performanceLogger;
    }

    public int getScalableStage() {
        return this.scalableStage;
    }

    public ExternalReader getPerformanceNumbers() {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("jautopipe.txt");
            br = new BufferedReader(fr);

            int i, count;
            count = 0;
            String s = "";
            while (s != null) {

                String[] split;
                s = br.readLine();

                switch (count) {
                    case 2:
                        s = s.substring(36);
                        split = s.split(" ms , tps : ");
                        split[1] = split[1].split(" req/sec")[0];

                        this.performanceLogger.addBefore(new PerformanceInstance(1, 1,
                                Double.parseDouble(split[0]), Double.parseDouble(split[1])));
                        break;
                    case 3:
                        s = s.substring(36);
                        split = s.split(" ms , tps : ");
                        split[1] = split[1].split(" req/sec")[0];

                        this.performanceLogger.addBefore(new PerformanceInstance(2, 1,
                                Double.parseDouble(split[0]), Double.parseDouble(split[1])));
                        break;
                    case 6:
                        this.scalableStage = Integer.parseInt(s.substring(17, 18));
                        break;
                    case 11:
                        s = s.substring(36);
                        split = s.split(" ms , tps : ");
                        split[1] = split[1].split(" req/sec")[0];

                        this.performanceLogger.addAfter(new PerformanceInstance(1, 1,
                                Double.parseDouble(split[0]), Double.parseDouble(split[1])));
                        break;
                    case 12:
                        s = s.substring(36);
                        split = s.split(" ms , tps : ");
                        split[1] = split[1].split(" req/sec")[0];

                        this.performanceLogger.addAfter(new PerformanceInstance(2, 1,
                                Double.parseDouble(split[0]), Double.parseDouble(split[1])));
                        break;
                }
                count++;

            }
            br.close();
            fr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ExternalReader(this.performanceLogger, this.scalableStage);

    }
}
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PerfReader {


    private PerformanceInstance befPerfRecords;
    private PerformanceInstance afPerfRecords;
    private double befLatency;
    private double afLatency;
    private int scaledHandlers;

    public static void main(String[] args) {
        PerfReader reader = new PerfReader();
        reader.getPerformanceNumbers();
    }

    public PerformanceInstance getBefPerfRecords() {
        return this.befPerfRecords;
    }

    public PerformanceInstance getAfPerfRecords() {
        return this.afPerfRecords;
    }

    public void getPerformanceNumbers() {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("jautopipe_type2.txt");
            br = new BufferedReader(fr);

            int count;
            count = 0;
            String s = "";

            while (s != null) {

                String[] split;
                s = br.readLine();

                switch (count) {
                    case 2:
                        s = s.substring(26, 27);
                        this.scaledHandlers = Integer.parseInt(s);
                        break;
                    case 5:
                        s = s.substring(10);
                        split = s.split(" mil");
                        this.befLatency = Double.parseDouble(split[0]);
                        break;
                    case 6:
                        s = s.substring(5);
                        split = s.split(" req");
                        this.befPerfRecords = new PerformanceInstance(1, -1,
                                this.befLatency, Double.parseDouble(split[0]));
                        break;
                    case 8:
                        s = s.substring(10);
                        split = s.split(" mil");
                        this.afLatency = Double.parseDouble(split[0]);
                        break;
                    case 9:
                        s = s.substring(5);
                        split = s.split(" req");
                        this.afPerfRecords = new PerformanceInstance(this.scaledHandlers, -1,
                                this.afLatency, Double.parseDouble(split[0]));
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

    }
}

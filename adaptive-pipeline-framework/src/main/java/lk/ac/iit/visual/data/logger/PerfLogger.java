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

package lk.ac.iit.visual.data.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PerfLogger {

    private static PerfLogger logger = null;
    private String fileName;
    private BufferedWriter bufferedWriter = null;
    private FileWriter fileWriter = null;


    private PerfLogger() {

        this.fileName = "jautopipe_type2.txt";
        try {
            this.fileWriter = new FileWriter(this.fileName);
            this.bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized static PerfLogger getLogger() {
        if (logger == null) {
            logger = new PerfLogger();
        }
        return logger;
    }

    public void log(String content) {


        try {

            bufferedWriter.write(content);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void close() {

        try {

            if (this.bufferedWriter != null)
                this.bufferedWriter.close();

            if (this.fileWriter != null)
                this.fileWriter.close();

        } catch (IOException ex) {

            ex.printStackTrace();

        }
    }
}

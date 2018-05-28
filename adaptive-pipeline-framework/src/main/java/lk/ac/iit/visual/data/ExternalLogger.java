package lk.ac.iit.visual.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExternalLogger {

    private static ExternalLogger logger = null;
    private String fileName;
    private BufferedWriter bufferedWriter = null;
    private FileWriter fileWriter = null;
    private ExternalLogger() {

        this.fileName = "jautopipe.txt";
        try {
            this.fileWriter = new FileWriter(this.fileName);
            this.bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized static ExternalLogger getLogger (){
        if(logger==null){
            logger = new ExternalLogger();
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

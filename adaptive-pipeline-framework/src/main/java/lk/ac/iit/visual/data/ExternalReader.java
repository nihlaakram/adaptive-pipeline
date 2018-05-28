package lk.ac.iit.visual.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExternalReader {


    private PerformanceLogger  performanceLogger = new PerformanceLogger();
    private int scalableStage;

    private ExternalReader (PerformanceLogger  performanceLogger, int scalableStage){
        this.performanceLogger = performanceLogger;
        this.scalableStage = scalableStage;

    }

    public ExternalReader(){

    }


    public PerformanceLogger getPerformanceLogger() {
        return this.performanceLogger;
    }

    public int getScalableStage() {
        return this.scalableStage;
    }

    public ExternalReader getPerformanceNumbers(){
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("jautopipe.txt");
             br=new BufferedReader(fr);

            int i,count;
            count = 0;
            String s = "";
            while(s != null){

                String[] split;
                s= br.readLine();

                switch (count){
                    case 2:
                        s= s.substring(36);
                        split = s.split(" ms , tps : ");
                        split[1] = split[1].split(" req/sec")[0];

                        this.performanceLogger.addBefore(new PerformanceInstance(1,1,
                                Double.parseDouble(split[0]), Double.parseDouble(split[1])));
                        break;
                    case 3:
                        s= s.substring(36);
                        split = s.split(" ms , tps : ");
                        split[1] = split[1].split(" req/sec")[0];

                        this.performanceLogger.addBefore(new PerformanceInstance(2,1,
                                Double.parseDouble(split[0]), Double.parseDouble(split[1])));
                        break;
                    case 6 :
                        this.scalableStage = Integer.parseInt(s.substring(17,18));
                        break;
                    case 11:
                        s= s.substring(36);
                        split = s.split(" ms , tps : ");
                        split[1] = split[1].split(" req/sec")[0];

                        this.performanceLogger.addAfter(new PerformanceInstance(1,1,
                                Double.parseDouble(split[0]), Double.parseDouble(split[1])));
                        break;
                    case 12:
                        s= s.substring(36);
                        split = s.split(" ms , tps : ");
                        split[1] = split[1].split(" req/sec")[0];

                        this.performanceLogger.addAfter(new PerformanceInstance(2,1,
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
package lk.ac.iit.visual.data;


import java.util.ArrayList;
import java.util.List;

public class PerformanceLogger {

    private ArrayList<PerformanceInstance> before = new ArrayList<>();
    private ArrayList<PerformanceInstance> after = new ArrayList<>();
    private static PerformanceLogger performanceLogger;

    public PerformanceLogger(){

    }

    public synchronized static PerformanceLogger getLogger(){
        if (performanceLogger==null){
            performanceLogger = new PerformanceLogger();
        }
        return performanceLogger;
    }

    public void addAfter(PerformanceInstance instance){
        this.after.add(instance);
    }

    public void addBefore(PerformanceInstance instance){
        this.before.add(instance);
    }

    public ArrayList<PerformanceInstance> getAfter(){
        return this.after;
    }

    public ArrayList<PerformanceInstance> getBefore(){
        return this.before;
    }

}

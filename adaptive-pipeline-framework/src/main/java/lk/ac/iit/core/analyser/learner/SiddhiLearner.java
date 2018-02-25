package lk.ac.iit.core.analyser.learner;

import lk.ac.iit.core.Monitor;
import lk.ac.iit.core.analyser.AnalyserData;
import lk.ac.iit.core.planner.Planner;
import lk.ac.iit.core.planner.PlannerData;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

import java.util.Set;

public class SiddhiLearner {
    private final InputHandler inputHandler;
    private final String query;
    private final String inStreamDefinition;
    private final SiddhiManager siddhiManager;
    private SiddhiAppRuntime siddhiAppRuntime;


    public SiddhiLearner(int monitorThreshold, int noOfStages) {
        TpsAttributeAggregator.monitorThreshold = monitorThreshold;
        this.inStreamDefinition = "define stream inputStream (tt long, tt1 long, tt2 long, tt3 long); " +
                "define stream outputStream (tt double, tt1 double, tt2 double, tt3 double);";
        this.query = "@info(name = 'query1') " + "from inputStream#window.lengthBatch("+monitorThreshold+") " +
                "select learner:latency(tt) as tt, learner:latency(tt1) as tt1, learner:tps(tt2) as tt2 , " +
                "learner:tps(tt3) as tt3 insert into filteredOutputStream";


        this.siddhiManager = new SiddhiManager();
        this.siddhiManager.setExtension("learner:latency", LatencyAttributeAggregator.class);
        this.siddhiManager.setExtension("learner:tps", TpsAttributeAggregator.class);
        this.siddhiAppRuntime = this.siddhiManager.
                createSiddhiAppRuntime(inStreamDefinition + query);
        this.inputHandler = this.siddhiAppRuntime.getInputHandler("inputStream");
        initCallback();
        this.siddhiAppRuntime.start();


    }

    private void initCallback() {
        this.siddhiAppRuntime.addCallback("filteredOutputStream", new StreamCallback() {
            @Override
            public void receive(org.wso2.siddhi.core.event.Event[] events) {
                for (Event ev : events) {
                    System.out.println(ev);//(double) ev.getData()[0], (double) ev.getData()[2]
                    double val = (double) ev.getData()[0];
                    double val2 = (double) ev.getData()[1];
                    double val3 = (double) ev.getData()[2];
                    double val4 = (double) ev.getData()[3];
                    AnalyserData analyserData = new AnalyserData(new double[]{val3, val4}, new double[]{val, val2});
                    Planner planner = new Planner(analyserData, 5);
                    PlannerData plannerData = planner.plan();
                    System.out.println(plannerData.isScalability() + "\t" + plannerData.getStageID() + "\t"+planner.getNoOfThread());

//                    Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//                    Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
//                    for(int i=0; i<threadArray.length;i++){
//                        System.out.println(threadArray[i]);
//                    }
                    if (plannerData.isScalability()) {
                       Monitor.getMonitor1().getExecutor().executeScaling(plannerData.getStageID());

                    }


                }
            }
        });
    }


    public void publish(Event obj) {
        try {
            inputHandler.send(obj);
        } catch (InterruptedException e) {

        }

    }


}

package lk.ac.iit.core.analyser.learner;

import lk.ac.iit.core.analyser.AnalyserData;
import lk.ac.iit.core.planner.Planner;
import lk.ac.iit.core.planner.PlannerData;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

public class SiddhiLearner {
    private final InputHandler inputHandler;
    private final String query;
    private final String inStreamDefinition;
    private final SiddhiManager siddhiManager;
    private SiddhiAppRuntime siddhiAppRuntime;

//
//        public void test1() throws InterruptedException {
//
//            log.info("MedianAggregatorTestCase Double Sliding Length Window TestCase");
//            SiddhiManager siddhiManager = siddhiManager;
//
//            String inStreamDefinition = "define stream inputStream (tt long); " +
//                    "define stream outputStream (tt double);";
//
//            String query = "@info(name = 'query1') " + "from inputStream#window.length(5) " +
//                    "select learner:latency(tt) as tt insert into filteredOutputStream";
//            SiddhiAppRuntime siddhiAppRuntime = siddhiManager.
//                    createSiddhiAppRuntime(inStreamDefinition + query);
//
//            siddhiAppRuntime.addCallback("filteredOutputStream", new StreamCallback() {
//                @Override
//                public void receive(Event[] events) {
//                    for (Event ev : events) {
//                        count++;
//                        switch (count){
//                            case 1 :
//                                Assert.assertEquals(1.0, (double) ev.getData()[0], 3);
//                                break;
//                            case 2 :
//                                Assert.assertEquals(2.0, (double) ev.getData()[0], 3);
//                                break;
//                            case 3 :
//                                Assert.assertEquals(3.0, (double) ev.getData()[0], 3);
//                                break;
//                            case 4 :
//                                Assert.assertEquals(4.0, (double) ev.getData()[0], 3);
//                                break;
//                            case 5 :
//                                Assert.assertEquals(5.0, (double) ev.getData()[0], 3);
//                                break;
//                            default:
//                                break;
//                        }
//
//
//
//
//                    }
//                }
//            });
//
//            InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
//            siddhiAppRuntime.start();
//            inputHandler.send(new Object[]{1l});
//            inputHandler.send(new Object[]{3l});
//            inputHandler.send(new Object[]{5l});
//            inputHandler.send(new Object[]{7l});
//            inputHandler.send(new Object[]{9l});
//            inputHandler.send(new Object[]{11l});
//            inputHandler.send(new Object[]{13l});
//            inputHandler.send(new Object[]{15l});
//
//            siddhiAppRuntime.shutdown();
//        }


    public SiddhiLearner() {
        this.inStreamDefinition = "define stream inputStream (tt long, tt1 long); " +
                    "define stream outputStream (tt double, tt1 double);";
        this.query = "@info(name = 'query1') " + "from inputStream#window.lengthBatch(1000) " +
                    "select learner:latency(tt) as tt, learner:latency(tt1) as tt1 insert into filteredOutputStream";



        this.siddhiManager = new SiddhiManager();
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
                    System.out.println(ev.getData()[0]);

                    AnalyserData analyserData = new AnalyserData(new double[]{1000.0, 250.0}, new double[]{val, val2});
                    Planner planner = new Planner(analyserData, 5);
                    PlannerData plannerData = planner.plan();
                    System.out.println(plannerData.isScalability()+"\t"+plannerData.getStageID());



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

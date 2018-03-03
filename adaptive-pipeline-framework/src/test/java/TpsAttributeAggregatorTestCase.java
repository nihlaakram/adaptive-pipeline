import lk.ac.iit.core.analyser.learner.extension.TpsAttributeAggregator;
import org.apache.log4j.Logger;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

public class TpsAttributeAggregatorTestCase {


    private static final Logger log = Logger.getLogger(TpsAttributeAggregatorTestCase.class);
    private int count = 0;

    @org.junit.Test
    public void test1() throws InterruptedException {

        log.info("Tps Double Fixed Length Window TestCase");
        TpsAttributeAggregator.monitorThreshold = 1000;
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "define stream inputStream (tt long); " +
                "define stream outputStream (tt double);";

        String query = "@info(name = 'query1') " + "from inputStream#window.lengthBatch(1000) " +
                "select learner:tps(tt) as tt insert into filteredOutputStream";
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.
                createSiddhiAppRuntime(inStreamDefinition + query);

        siddhiAppRuntime.addCallback("filteredOutputStream", new StreamCallback() {
            @Override
            public void receive(Event[] events) {
                for (Event ev : events) {
                    System.out.println(ev);
//                    count++;
//                    switch (count) {
//                        case 1:
//                            Assert.assertEquals(1.0, (double) ev.getData()[0], 3);
//                            break;
//                        case 2:
//                            Assert.assertEquals(2.0, (double) ev.getData()[0], 3);
//                            break;
//                        case 3:
//                            Assert.assertEquals(3.0, (double) ev.getData()[0], 3);
//                            break;
//                        case 4:
//                            Assert.assertEquals(4.0, (double) ev.getData()[0], 3);
//                            break;
//                        case 5:
//                            Assert.assertEquals(5.0, (double) ev.getData()[0], 3);
//                            break;
//                        default:
//                            break;
//                    }


                }
            }
        });


        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        for (int i = 0; i < 999; i++) {
            inputHandler.send(new Object[]{0l});
        }
        inputHandler.send(new Object[]{1l});

        siddhiAppRuntime.shutdown();
    }
}

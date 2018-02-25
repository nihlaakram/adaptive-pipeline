import lk.ac.iit.core.analyser.learner.extension.LatencyAttributeAggregator;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.wso2.siddhi.core.SiddhiAppRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.stream.output.StreamCallback;

public class LatencyAggregatorTestCase {


    private static final Logger log = Logger.getLogger(LatencyAttributeAggregator.class);
    private int count = 0;

    @org.junit.Test
    public void test1() throws InterruptedException {

        log.info("MedianAggregatorTestCase Double Sliding Length Window TestCase");
        SiddhiManager siddhiManager = new SiddhiManager();

        String inStreamDefinition = "define stream inputStream (tt long); " +
                "define stream outputStream (tt double);";

        String query = "@info(name = 'query1') " + "from inputStream#window.length(5) " +
                "select learner:latency(tt) as tt insert into filteredOutputStream";
        SiddhiAppRuntime siddhiAppRuntime = siddhiManager.
                createSiddhiAppRuntime(inStreamDefinition + query);

        siddhiAppRuntime.addCallback("filteredOutputStream", new StreamCallback() {
            @Override
            public void receive(Event[] events) {
                for (Event ev : events) {
                    count++;
                    switch (count) {
                        case 1:
                            Assert.assertEquals(1.0, (double) ev.getData()[0], 3);
                            break;
                        case 2:
                            Assert.assertEquals(2.0, (double) ev.getData()[0], 3);
                            break;
                        case 3:
                            Assert.assertEquals(3.0, (double) ev.getData()[0], 3);
                            break;
                        case 4:
                            Assert.assertEquals(4.0, (double) ev.getData()[0], 3);
                            break;
                        case 5:
                            Assert.assertEquals(5.0, (double) ev.getData()[0], 3);
                            break;
                        default:
                            break;
                    }


                }
            }
        });

        InputHandler inputHandler = siddhiAppRuntime.getInputHandler("inputStream");
        siddhiAppRuntime.start();
        inputHandler.send(new Object[]{1l});
        inputHandler.send(new Object[]{3l});
        inputHandler.send(new Object[]{5l});
        inputHandler.send(new Object[]{7l});
        inputHandler.send(new Object[]{9l});
        inputHandler.send(new Object[]{11l});
        inputHandler.send(new Object[]{13l});
        inputHandler.send(new Object[]{15l});

        siddhiAppRuntime.shutdown();
    }

}



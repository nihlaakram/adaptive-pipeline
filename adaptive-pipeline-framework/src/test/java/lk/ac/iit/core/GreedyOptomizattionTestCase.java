package lk.ac.iit.core;

import lk.ac.iit.core.analyser.GreedyOptimizer;
import lk.ac.iit.core.analyser.data.AnalyserData;
import org.junit.Assert;

public class GreedyOptomizattionTestCase {
    /**
     * [0.9698, 1.275, 5387.931034482758, 5387.931034482758], isExpired=false}
     * true    1       4
     * Scaling Stage :1
     * Event{timestamp=1522998613623, data=[0.4515, 0.9862, 7012.622720897616, 7012.622720897616], isExpired=false}
     */

    @org.junit.Test
    public void Test1() throws InterruptedException {

        //log.info("WorkLoadModel TestCase 01");
        double[] lat = new double[]{0.9698, 1.275};
        double[] tps = new double[]{5387.931034482758, 5387.931034482758};
        GreedyOptimizer greedyOptimizer = new GreedyOptimizer(new AnalyserData(tps, lat));
        Assert.assertEquals(2, greedyOptimizer.analyse().getHandlerID());

    }
}

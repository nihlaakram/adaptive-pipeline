import lk.ac.iit.core.analyser.AnalyserData;
import lk.ac.iit.core.planner.Planner;
import org.junit.Assert;

import java.util.logging.Logger;

public class PlannerTestCase {

    private static final Logger log = Logger.getLogger(String.valueOf(Planner.class));

    @org.junit.Test
    public void Test1() throws InterruptedException {

        log.info("Planner TestCase 01");

        AnalyserData data = new AnalyserData(new double[]{1000.0, 250.0}, new double[]{1.0, 5.0});

        Planner planner = new Planner(data);
        Assert.assertEquals(planner.getNoOfThread(), 1);

    }

    @org.junit.Test
    public void Test2() throws InterruptedException {

        log.info("Planner TestCase 02");

        AnalyserData data = new AnalyserData(new double[]{1000.0, 250.0}, new double[]{1.0, 5.0});

        Planner planner = new Planner(data);
        Assert.assertArrayEquals(new double[]{1000.0, 250.0},planner.getData().getTpsArr(),3 );

    }

    @org.junit.Test
    public void Test3() throws InterruptedException {

        log.info("Planner TestCase 03");

        AnalyserData data = new AnalyserData(new double[]{1000.0, 250.0}, new double[]{1.0, 5.0});

        Planner planner = new Planner(data);
        Assert.assertArrayEquals(new double[]{1.0, 5.0},planner.getData().getAvgLatency(),3 );

    }

    @org.junit.Test
    public void Test4() throws InterruptedException {

        log.info("Planner TestCase 04");

        AnalyserData data = new AnalyserData(new double[]{1000.0, 250.0}, new double[]{1.0, 5.0});

        Planner planner = new Planner(data, 0);
       // Assert.assertEquals(false,planner.systemScale());

    }

    @org.junit.Test
    public void Test5() throws InterruptedException {

        log.info("Planner TestCase 05");

        AnalyserData data = new AnalyserData(new double[]{1000.0, 250.0}, new double[]{1.0, 5.0});

        Planner planner = new Planner(data, 1);
       // Assert.assertEquals(false,planner.systemScale());

    }

    @org.junit.Test
    public void Test6() throws InterruptedException {

        log.info("Planner TestCase 06");

        AnalyserData data = new AnalyserData(new double[]{1000.0, 250.0}, new double[]{1.0, 5.0});

        Planner planner = new Planner(data, 2);
       // Assert.assertEquals(true,planner.systemScale());

    }

    @org.junit.Test
    public void Test() throws InterruptedException {

        log.info("Planner TestCase 04");

        AnalyserData data = new AnalyserData(new double[]{1000.0, 250.0}, new double[]{1.0, 5.0});

        Planner planner = new Planner(data, -1);
        //Assert.assertEquals(false,planner.systemScale());

    }
}

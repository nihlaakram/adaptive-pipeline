package lk.ac.iit.core.analyser.workload;

import lk.ac.iit.core.analyser.workload.WorkloadModel;
import org.junit.Assert;

import java.util.logging.Logger;

public class WorkloadTestCase {

    private static final Logger log = Logger.getLogger(String.valueOf(WorkloadModel.class));



    @org.junit.Test
    public void Test1() throws InterruptedException {

        log.info("WorkloadModel TestCase 01");
        WorkloadModel model = new WorkloadModel();

        model.addWorkers(1, 10, 1);//10b
        model.addWorkers(1, 100, 1);//100b
        model.addWorkers(1, 1000, 1);//1kb
        model.addWorkers(1, 10000, 5);//10kb
        model.addWorkers(1, 100000, 5);//100kb
        model.addWorkers(1, 1000000, 5);//1mb
        model.addWorkers(1, 10000000, 5);//0mb

        Assert.assertEquals(5, model.getWorkers(1, 10000000));

    }

    @org.junit.Test
    public void Test2() throws InterruptedException {

        log.info("WorkloadModel TestCase 02");
        WorkloadModel model = new WorkloadModel();
        model.addWorkers(1, 10, 1);//10b
        model.addWorkers(1, 100, 1);//100b
        model.addWorkers(1, 1000, 1);//1kb
        model.addWorkers(1, 10000, 5);//10kb
        model.addWorkers(1, 100000, 5);//100kb
        model.addWorkers(1, 1000000, 5);//1mb
        model.addWorkers(1, 10000000, 5);//0mb

        Assert.assertEquals(5, model.getWorkers(1, 1000000));

    }

    @org.junit.Test
    public void Test3() throws InterruptedException {

        log.info("WorkloadModel TestCase 03");
        WorkloadModel model = new WorkloadModel();
        model.addWorkers(1, 10, 1);//10b
        model.addWorkers(1, 100, 1);//100b
        model.addWorkers(1, 1000, 1);//1kb
        model.addWorkers(1, 10000, 5);//10kb
        model.addWorkers(1, 100000, 5);//100kb
        model.addWorkers(1, 1000000, 5);//1mb
        model.addWorkers(1, 10000000, 5);//0mb

        Assert.assertEquals(5, model.getWorkers(1, 100000));

    }

    @org.junit.Test
    public void Test4() throws InterruptedException {

        log.info("WorkloadModel TestCase 04");
        WorkloadModel model = new WorkloadModel();
        model.addWorkers(1, 10, 1);//10b
        model.addWorkers(1, 100, 1);//100b
        model.addWorkers(1, 1000, 1);//1kb
        model.addWorkers(1, 10000, 5);//10kb
        model.addWorkers(1, 100000, 5);//100kb
        model.addWorkers(1, 1000000, 5);//1mb
        model.addWorkers(1, 10000000, 5);//0mb

        Assert.assertEquals(5, model.getWorkers(1, 10000));

    }

    @org.junit.Test
    public void Test5() throws InterruptedException {

        log.info("WorkloadModel TestCase 05");
        WorkloadModel model = new WorkloadModel();
        model.addWorkers(1, 10, 1);//10b
        model.addWorkers(1, 100, 1);//100b
        model.addWorkers(1, 1000, 1);//1kb
        model.addWorkers(1, 10000, 5);//10kb
        model.addWorkers(1, 100000, 5);//100kb
        model.addWorkers(1, 1000000, 5);//1mb
        model.addWorkers(1, 10000000, 5);//0mb

        Assert.assertEquals(1, model.getWorkers(1, 1000));

    }

    @org.junit.Test
    public void Test6() throws InterruptedException {

        log.info("WorkloadModel TestCase 06");
        WorkloadModel model = new WorkloadModel();
        model.addWorkers(1, 10, 1);//10b
        model.addWorkers(1, 100, 1);//100b
        model.addWorkers(1, 1000, 1);//1kb
        model.addWorkers(1, 10000, 5);//10kb
        model.addWorkers(1, 100000, 5);//100kb
        model.addWorkers(1, 1000000, 5);//1mb
        model.addWorkers(1, 10000000, 5);//0mb

        Assert.assertEquals(1, model.getWorkers(1, 1000));

    }

    @org.junit.Test
    public void Test7() throws InterruptedException {

        log.info("WorkloadModel TestCase 07");
        WorkloadModel model = new WorkloadModel();
        model.addWorkers(1, 10, 1);//10b
        model.addWorkers(1, 100, 1);//100b
        model.addWorkers(1, 1000, 1);//1kb
        model.addWorkers(1, 10000, 5);//10kb
        model.addWorkers(1, 100000, 5);//100kb
        model.addWorkers(1, 1000000, 5);//1mb
        model.addWorkers(1, 10000000, 5);//0mb

        Assert.assertEquals(1, model.getWorkers(1, 10));

    }



}

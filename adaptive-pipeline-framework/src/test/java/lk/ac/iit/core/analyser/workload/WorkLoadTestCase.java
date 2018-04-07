package lk.ac.iit.core.analyser.workload;

import org.junit.Assert;

import java.util.logging.Logger;

public class WorkLoadTestCase {
    private static final Logger log = Logger.getLogger(String.valueOf(WorkLoadModel.class));

    @org.junit.Test
    public void Test1() throws InterruptedException {

        log.info("WorkLoadModel TestCase 01");
        WorkLoadModel model = new WorkLoadModel();

        model.addWorkers(10, 1);//10b
        model.addWorkers(100, 1);//100b
        model.addWorkers(1000, 1);//1kb
        model.addWorkers(10000, 5);//10kb
        model.addWorkers(100000, 5);//100kb
        model.addWorkers(1000000, 5);//1mb
        model.addWorkers(10000000, 5);//0mb

        Assert.assertEquals(5, model.getWorkers(10000000));

    }

    @org.junit.Test
    public void Test2() throws InterruptedException {

        log.info("WorkLoadModel TestCase 02");
        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(10, 1);//10b
        model.addWorkers(100, 1);//100b
        model.addWorkers(1000, 1);//1kb
        model.addWorkers(10000, 5);//10kb
        model.addWorkers(100000, 5);//100kb
        model.addWorkers(1000000, 5);//1mb
        model.addWorkers(10000000, 5);//0mb

        Assert.assertEquals(5, model.getWorkers(1000000));

    }

    @org.junit.Test
    public void Test3() throws InterruptedException {

        log.info("WorkLoadModel TestCase 03");
        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(10, 1);//10b
        model.addWorkers(100, 1);//100b
        model.addWorkers(1000, 1);//1kb
        model.addWorkers(10000, 5);//10kb
        model.addWorkers(100000, 5);//100kb
        model.addWorkers(1000000, 5);//1mb
        model.addWorkers(10000000, 5);//0mb

        Assert.assertEquals(5, model.getWorkers(100000));

    }

    @org.junit.Test
    public void Test4() throws InterruptedException {

        log.info("WorkLoadModel TestCase 04");
        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(10, 1);//10b
        model.addWorkers(100, 1);//100b
        model.addWorkers(1000, 1);//1kb
        model.addWorkers(10000, 5);//10kb
        model.addWorkers(100000, 5);//100kb
        model.addWorkers(1000000, 5);//1mb
        model.addWorkers(10000000, 5);//0mb

        Assert.assertEquals(5, model.getWorkers(10000));

    }

    @org.junit.Test
    public void Test5() throws InterruptedException {

        log.info("WorkLoadModel TestCase 05");
        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(10, 1);//10b
        model.addWorkers(100, 1);//100b
        model.addWorkers(1000, 1);//1kb
        model.addWorkers(10000, 5);//10kb
        model.addWorkers(100000, 5);//100kb
        model.addWorkers(1000000, 5);//1mb
        model.addWorkers(10000000, 5);//0mb

        Assert.assertEquals(1, model.getWorkers(1000));

    }

    @org.junit.Test
    public void Test6() throws InterruptedException {

        log.info("WorkLoadModel TestCase 06");
        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(10, 1);//10b
        model.addWorkers(100, 1);//100b
        model.addWorkers(1000, 1);//1kb
        model.addWorkers(10000, 5);//10kb
        model.addWorkers(100000, 5);//100kb
        model.addWorkers(1000000, 5);//1mb
        model.addWorkers(10000000, 5);//0mb

        Assert.assertEquals(1, model.getWorkers(1000));

    }

    @org.junit.Test
    public void Test7() throws InterruptedException {

        log.info("WorkLoadModel TestCase 07");
        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(10, 1);//10b
        model.addWorkers(100, 1);//100b
        model.addWorkers(1000, 1);//1kb
        model.addWorkers(10000, 5);//10kb
        model.addWorkers(100000, 5);//100kb
        model.addWorkers(1000000, 5);//1mb
        model.addWorkers(10000000, 5);//0mb

        Assert.assertEquals(1, model.getWorkers(10));

    }

}

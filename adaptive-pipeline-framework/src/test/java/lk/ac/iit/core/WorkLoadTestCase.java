package lk.ac.iit.core;

import lk.ac.iit.core.analyser.workload.WorkLoadModel;
import org.junit.Assert;

public class WorkLoadTestCase {

    @org.junit.Test
    public void Test1() throws InterruptedException {

        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(-1,10, 1);//10b
        model.addWorkers(-1,100, 1);//100b
        model.addWorkers(-1,1000, 1);//1kb
        model.addWorkers(-1,10000, 5);//10kb
        model.addWorkers(-1,100000, 5);//100kb
        model.addWorkers(-1,1000000, 5);//1mb
        model.addWorkers(-1,10000000, 5);//0mb

        model.addWorkers(1000,10, 1);//10b
        model.addWorkers(1000,100, 1);//100b
        model.addWorkers(1000,1000, 1);//1kb
        model.addWorkers(1000,10000, 2);//10kb
        model.addWorkers(1000,100000, 3);//100kb
        model.addWorkers(1000,1000000, 5);//1mb
        model.addWorkers(1000,10000000, 4);//0mb

        model.addWorkers(100,10, 1);//10b
        model.addWorkers(100,100, 1);//100b
        model.addWorkers(100,1000, 1);//1kb
        model.addWorkers(100,10000, 2);//10kb
        model.addWorkers(100,100000, 3);//100kb
        model.addWorkers(100,1000000, 3);//1mb
        model.addWorkers(100,10000000, 4);//0mb

        model.addWorkers(10,10, 1);//10b
        model.addWorkers(10,100, 1);//100b
        model.addWorkers(10,1000, 1);//1kb
        model.addWorkers(10,10000, 2);//10kb
        model.addWorkers(10,100000, 3);//100kb
        model.addWorkers(10,1000000, 3);//1mb
        model.addWorkers(10,10000000, 4);//0mb

        model.addWorkers(1,10, 1);//10b
        model.addWorkers(1,100, 1);//100b
        model.addWorkers(1,1000, 1);//1kb
        model.addWorkers(1,10000, 1);//10kb
        model.addWorkers(1,100000, 2);//100kb
        model.addWorkers(1,1000000, 3);//1mb
        model.addWorkers(1,10000000, 3);//0mb

        Assert.assertEquals(3, model.getWorkers(1000, 100000));

    }
}

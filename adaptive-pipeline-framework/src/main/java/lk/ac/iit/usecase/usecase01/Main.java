package lk.ac.iit.usecase.usecase01;

import lk.ac.iit.core.Monitor;
import lk.ac.iit.core.analyser.workload.WorkLoadModel;


public class Main {
    public static void main(String[] args) {

        WorkLoadModel model = populateModel();

        System.out.println(model.getWorkers(1, 10000000));



    }

    public static WorkLoadModel populateModel(){
        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(1, 10, 1);//10b
        model.addWorkers(1, 100, 1);//100b
        model.addWorkers(1, 1000, 1);//1kb
        model.addWorkers(1, 10000, 5);//10kb
        model.addWorkers(1, 100000, 5);//100kb
        model.addWorkers(1, 1000000, 5);//1mb
        model.addWorkers(1, 10000000, 5);//0mb
        return model;
    }
}

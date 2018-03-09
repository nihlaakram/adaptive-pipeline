package lk.ac.iit.usecase.usecase01;

import lk.ac.iit.core.analyser.workload.WorkLoadModel;


class Key {

    public int a;
    public int b;

    public Key(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public Key getKey() {
        return null;
    }

    public boolean equals(Object obj) {

        if (obj instanceof Key) {

            Key p = (Key) obj;

            return ((this.a == p.a) && (this.b == p.b));

        }

        return false;

    }


    public int hashCode() {

        return (31 * a) ^ b;

    }


}

public class Main {
    public static void main(String[] args) {

        WorkLoadModel model = new WorkLoadModel();
        model.addWorkers(1, 10, 1);//10b
        model.addWorkers(1, 100, 1);//100b
        model.addWorkers(1, 1000, 1);//1kb
        model.addWorkers(1, 10000, 5);//10kb
        model.addWorkers(1, 100000, 5);//100kb
        model.addWorkers(1, 1000000, 5);//1mb
        model.addWorkers(1, 10000000, 5);//0mb
        System.out.println(model.getWorkers(1, 10000000));



    }
}

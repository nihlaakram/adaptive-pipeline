package lk.ac.iit.usecase.usecase01;

import lk.ac.iit.core.analyser.workload.WorkloadModel;

import java.util.HashMap;
import java.util.Map;


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

        WorkloadModel model = new WorkloadModel(7);

        Map<Key, Integer> m1 = new HashMap();
//        m1.put("Zara", "8");
//        m1.put("Mahnaz", "31");
//        m1.put("Ayan", "12");
//        m1.put("Daisy", "14");
        m1.put(new Key(1, 1), 100);

        System.out.println(m1.size());
        System.out.println(" Map Elements");
        System.out.print("\t" + m1.get(new Key(1, 1)));
    }
}

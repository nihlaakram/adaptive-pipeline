package lk.ac.iit.main;

// An object reference of this class is
// contained by Test2
class Test {
    int x, y;
}


// Contains a reference of Test and implements
// clone with deep copy.
class Test2 implements Cloneable, Runnable {
    int a, b;

    Test c = new Test();

    public Object clone() throws
            CloneNotSupportedException {
        // Assign the shallow copy to new refernce variable t
        Test2 t = (Test2) super.clone();
        return t;
    }

    @Override
    public void run() {
        System.out.println("Hello");
    }
}

public class AfterRenderTest {


    //public static void main(String[] args) {
//        Hlp t1 = new Hlp("Shaf");
//
//
//        t1.start();
//
//        Thread t2 = new Hlp(t1.name1+"Nihla");
//        t2.start();

    public static void main(String args[]) throws
            CloneNotSupportedException {
        Test2 t1 = new Test2();
        t1.a = 10;
        t1.b = 20;
        t1.c.x = 30;
        t1.c.y = 40;
        Thread thread1 = new Thread(t1);
        System.out.println(t1.c);
        thread1.start();


        Test2 t3 = (Test2) t1.clone();
        t3.a = 100;


        // Change in primitive type of t2 will not
        // be reflected in t1 field
        t3.c.x = 300;
        Thread thread2 = new Thread(t3);
        System.out.println(t3.c);
        thread2.start();

        // Change in object type field of t2 will not
        // be reflected in t1(deep copy)
        System.out.println(t1.a + " " + t1.b + " " +
                t1.c.x + " " + t1.c.y);
        System.out.println(t3.a + " " + t3.b + " " +
                t3.c.x + " " + t3.c.y);
    }


    //}


}



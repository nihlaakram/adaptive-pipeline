package lk.ac.iit.main;

public class Test {

    static String name = "hello";
    public static void main(String[] args) {
        Class c = Test.class;

        try {
            Object ob = c.newInstance();
            Test test1 = new Test();
            Test test = (Test) ob;
            test.name = "123";
            System.out.println(test1.name);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

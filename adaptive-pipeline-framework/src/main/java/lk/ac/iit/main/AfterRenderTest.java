package lk.ac.iit.main;

public class AfterRenderTest {

    public static void main(String[] args) {

        AfterRenderTest aft = new AfterRenderTest();

        aft.after().hadle();

        aft.after();

        aft.hadle();

    }

    public AfterRenderTest(){
        System.out.println("Constructor");
    }

    public AfterRenderTest after(){
        System.out.println("H1");
        return this;
    }

    public  AfterRenderTest hadle(){
        System.out.println("H2");
        return this;
    }
}

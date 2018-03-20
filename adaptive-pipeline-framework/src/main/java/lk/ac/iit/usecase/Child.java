package lk.ac.iit.usecase;

public class Child extends Parent{

    public Child(Integer a) {
        super(a);
    }

    public void getA(){
        System.out.println(this.a);
    }
}

class Parent{

    int a;

    public Parent (Integer a){
        this.a = a;

    }
}


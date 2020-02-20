package com.test.web;

public class FieldClass {
    public static void main(String[] args) {
        System.out.println(Sub.A);
//        System.out.println(Subimpl.A);
        System.out.println(Sub.A);
    }
}

interface interface0{
    int A = 0;
}

interface interface1 extends  interface0{
    int A = 1;
}

interface interface2{
    int A = 2;
}

class Parent implements interface0{
    public static int A = 3;
}

class Sub extends Parent implements interface1,interface2{
    public static int A = 4;
}

class Subimpl implements interface1,interface2{

}

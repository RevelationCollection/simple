package com.test.web;

public class StaticClass extends SuperClass{
    public static int a = 1;

    public static int b ;

    public static final int c = 3;

    public static final int d ;

    static {
        System.out.println("a="+a);
        System.out.println("b="+b);
        System.out.println("c="+c);
        d = 4 ;
        System.out.println("d="+d);
    }
}

class SuperClass{
    public static int SUB_A = 1;

    public static final int SUB_B = 2;

    static {

        System.out.println("Super class init");
    }
}

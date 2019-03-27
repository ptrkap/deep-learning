package com.epam;

public class Foobar {

    public static void main(String[] args) {
        Foobar foobar = new Foobar();
        System.out.println(foobar.add(3, 5));
    }

    public int add(int a, int b) {
        return a + b;
    }
}

package com.hades.android.example.gson;

public class Person {
    String name;
    int age;
    Address address;

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                '}';
    }
}

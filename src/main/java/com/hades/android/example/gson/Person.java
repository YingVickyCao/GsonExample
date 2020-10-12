package com.hades.android.example.gson;

public class Person {
    public String name;
    public int age;
    public Address address;

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                '}';
    }
}

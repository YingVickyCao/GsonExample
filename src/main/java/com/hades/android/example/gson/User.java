package com.hades.android.example.gson;

public class User {
    String name;
    String email;
    boolean isDeveloper;
    int age;

    public User(String name, String email, boolean isDeveloper, int age) {
        this.name = name;
        this.email = email;
        this.isDeveloper = isDeveloper;
        this.age = age;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isDeveloper=" + isDeveloper +
                ", age=" + age +
                '}';
    }
}

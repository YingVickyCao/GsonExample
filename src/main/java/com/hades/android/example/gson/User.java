package com.hades.android.example.gson;

public class User {
    public String name;
    public String email;
    public boolean isDeveloper;
    public int age;

    public User() {
    }

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

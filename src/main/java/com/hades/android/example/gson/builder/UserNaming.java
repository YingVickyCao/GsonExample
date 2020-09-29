package com.hades.android.example.gson.builder;

public class UserNaming {
    String Name;
    String email_of_developer;
    boolean isDeveloper;
    int _ageOfDeveloper;

    public UserNaming(String name, String email_of_developer, boolean isDeveloper, int ageOfDeveloper) {
        Name = name;
        this.email_of_developer = email_of_developer;
        this.isDeveloper = isDeveloper;
        _ageOfDeveloper = ageOfDeveloper;
    }
}

package com.hades.android.example.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hades.android.example.gson.builder.PostReviewer;
import com.hades.android.example.gson.builder.UserNaming;

public class TestGsonBuilder {

    public static void main(String[] args) {
        TestGsonBuilder test = new TestGsonBuilder();
        test.init();
        test.current();
    }

    private void init() {

    }

    private void current() {
        lower_case_with_underscores();
    }

    private void all() {
        identity();
        lower_case_with_underscores();
        lower_case_with_dashes();
        upper_camel_case();
        upper_camel_case_with_spaces();
    }

    private void identity() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        Gson gson = gsonBuilder.create();

        UserNaming user = new UserNaming("Norman", "norman@futurestud.io", true, 26);
        String usersJson = gson.toJson(user);
        System.out.println(usersJson); // {"Name":"Norman","email_of_developer":"norman@futurestud.io","isDeveloper":true,"_ageOfDeveloper":26}
    }

    private void lower_case_with_underscores() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = gsonBuilder.create();

        UserNaming user = new UserNaming("Norman", "norman@futurestud.io", true, 26);
        String usersJson = gson.toJson(user);
        System.out.println(usersJson);// {"name":"Norman","email_of_developer":"norman@futurestud.io","is_developer":true,"_age_of_developer":26}

        String json2 = "{\"reviewer_name\": \"Marcus\"}";
        PostReviewer bean = gson.fromJson(json2, PostReviewer.class);
        System.out.println(bean.toString()); // {reviewerName='Marcus'}
    }

    private void lower_case_with_dashes() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES);
        Gson gson = gsonBuilder.create();

        UserNaming user = new UserNaming("Norman", "norman@futurestud.io", true, 26);
        String usersJson = gson.toJson(user);
        System.out.println(usersJson);// {"name":"Norman","email_of_developer":"norman@futurestud.io","is-developer":true,"_age-of-developer":26}
    }

    private void upper_camel_case() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
        Gson gson = gsonBuilder.create();

        UserNaming user = new UserNaming("Norman", "norman@futurestud.io", true, 26);
        String usersJson = gson.toJson(user);
        System.out.println(usersJson);// {"Name":"Norman","Email_of_developer":"norman@futurestud.io","IsDeveloper":true,"_AgeOfDeveloper":26}
    }

    private void upper_camel_case_with_spaces() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES);
        Gson gson = gsonBuilder.create();

        UserNaming user = new UserNaming("Norman", "norman@futurestud.io", true, 26);
        String usersJson = gson.toJson(user);
        System.out.println(usersJson);// {"Name":"Norman","Email_of_developer":"norman@futurestud.io","Is Developer":true,"_Age Of Developer":26}
    }
}

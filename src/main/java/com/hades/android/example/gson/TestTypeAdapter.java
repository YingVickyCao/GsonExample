package com.hades.android.example.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.hades.android.example.gson.type_adapter.UserTypeAdapter;

import java.io.IOException;

public class TestTypeAdapter {
    public static void main(String[] args) {
        TestTypeAdapter test = new TestTypeAdapter();
        test.current();
    }

    private void current() {
        test1();
    }

    private void all() {
    }

    private void test1() {
        User user = new User();
        user.name = "A";
        user.age = 20;
        user.email = "example@google.com";

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .registerTypeAdapter(Integer.class, new TypeAdapter<Integer>() {//接管【Integer】类型的序列化和反序列化过程
                    @Override
                    public void write(JsonWriter out, Integer value) throws IOException {
                        out.value(String.valueOf(value));
                    }

                    @Override
                    public Integer read(JsonReader in) throws IOException {
                        try {
                            return Integer.parseInt(in.nextString());
                        } catch (NumberFormatException e) {
                            return -1;
                        }
                    }
                })
                .registerTypeAdapter(int.class, new TypeAdapter<Integer>() {
                    @Override
                    public void write(JsonWriter out, Integer value) throws IOException {
                        out.value(String.valueOf(value));
                    }

                    @Override
                    public Integer read(JsonReader in) throws IOException {
                        try {
                            return Integer.parseInt(in.nextString());
                        } catch (NumberFormatException e) {
                            return -2;
                        }
                    }
                })
                .create();
        System.out.println(gson.toJson(user));// {"name":"A","age":20}

        {
            String json = "{\"name\":\"A\",\"age\":-10}";
            User dest = gson.fromJson(json, User.class);
            System.out.println(dest.toString()); // {name='A', email='null', isDeveloper=false, age=0}
        }
//        {
//            String json = "[" +
//                    "{\"name\":\"A\",\"age\":25}" +
//                    ",{\"name\":\"B\",\"age\":-10}" +
//                    ",{\"name\":\"C\",\"age\":\"-abc\"}" +
//                    "]";
//            List<User> dest = gson.fromJson(json, new TypeToken<List<User>>() {
//            }.getType());
//            System.out.println(dest.toString()); // [{name='A', email='null', isDeveloper=false, age=25}, {name='B', email='null', isDeveloper=false, age=0}]
//        }
    }
}
package com.hades.android.example.gson.type_adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.hades.android.example.gson.User;

import java.io.IOException;

public class UserTypeAdapter extends TypeAdapter<User> {
    @Override
    public void write(JsonWriter out, User value) throws IOException {
        out.beginObject();
        out.name("name").value(value.name);
        out.name("age").value(value.age);
        out.endObject();
    }

    @Override
    public User read(JsonReader in) throws IOException {
        User user = new User();

        in.setLenient(true);
        if (JsonToken.BEGIN_OBJECT.equals(in.peek())) {
            in.beginObject();
        }
        if (JsonToken.BEGIN_ARRAY.equals(in.peek())) {
            in.beginArray();
        }

        while (in.hasNext()) {
            switch (in.nextName()) {
                case "name":
                    user.name = in.nextString();
                    break;
                case "age":
                    try {
                        if (in.peek() == JsonToken.STRING) {
                            in.nextString();
                            user.age = 0;
                        } else {
                            user.age = in.nextInt();
                        }
                    } catch (Exception e) {
                        user.age = 0;
                        in.skipValue();
                    }
                    break;
            }
        }


        if (JsonToken.END_OBJECT.equals(in.peek())) {
            in.endObject();
        }

        if (JsonToken.END_ARRAY.equals(in.peek())) {
            in.endArray();
        }
        return user;
    }
}

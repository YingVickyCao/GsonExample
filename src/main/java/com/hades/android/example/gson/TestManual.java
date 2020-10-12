package com.hades.android.example.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class TestManual {
    public static void main(String[] args) {
        TestManual test = new TestManual();
        test.init();
        test.current();
    }

    private void init() {

    }

    private void current() {
        manual_Serialization_JsonWriter();
    }

    private void all() {
        String json1 = "[" +
                "{\"name\":\"A\",\"age\":25}" +
                ",{\"name\":\"B\",\"age\":24}" +
                "]";

        String json2 = "{\"name\":\"A\",\"age\":25}";
        manual_Deserialization_JsonParser(json1);
        manual_Deserialization_JsonParser(json2);

        manual_Deserialization_JsonReader(json1);
        manual_Deserialization_JsonReader(json2);
    }

    // 手动序列化和反序列化
    private void manual_Deserialization_JsonParser(String json) {
        List<Person> personList = new ArrayList<>();
        Person person = new Person();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            for (JsonElement item : array) {
                Person t = readItem(item);
                if (t != null) {
                    personList.add(t);
                }
            }
            System.out.println(personList.toString());  // [{name='A', age=25, address=null}, {name='B', age=24, address=null}]
        } else if (element.isJsonObject()) {
            person = readItem(element);
            System.out.println(person.toString());  // {name='A', age=25, address=null}
        }
    }

    private Person readItem(JsonElement item) {
        if (item.isJsonObject()) {
            Person person = new Person();
            JsonObject bean = item.getAsJsonObject();
            if (bean.has("name")) {
                person.name = bean.get("name").getAsString();
            }
            if (bean.has("age")) {
                person.age = bean.get("age").getAsInt();
            }
            return person;
        }
        return null;
    }

    private void manual_Serialization_JsonWriter() {
        try {
            // {"name":"A","age":25}
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(System.out));
            writer.beginObject()
                    .name("name").value("A")
                    .name("age").value(25)
                    .endObject();
//            System.out.println(writer.toString());;
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void manual_Deserialization_JsonReader(String json) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);

        Person person;
        List<Person> personList = new ArrayList<>();

        try {
            JsonToken nextToken = reader.peek();
            if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
                reader.beginObject();
                person = readItem(reader);
                System.out.println(person.toString());  // {name='null', age=0, address=null}
            }
            if (JsonToken.END_OBJECT.equals(nextToken)) {
                reader.endObject();
            }
            if (JsonToken.BEGIN_ARRAY.equals(nextToken)) {
                reader.beginArray();
                while (reader.hasNext()) {
                    Person t = readItem(reader);
                    personList.add(t);
                }
                System.out.println(personList.toString());  // [{name='A', age=0, address=null}, {name='null', age=25, address=null}]
            }

            if (JsonToken.END_ARRAY.equals(nextToken)) {
                reader.endArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Person readItem(JsonReader reader) {
        try {
            Person person = new Person();

            JsonToken nextToken = reader.peek();
            if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
                reader.beginObject();
            }
            if (JsonToken.END_OBJECT.equals(nextToken)) {
                reader.endObject();
            }
            String name = reader.nextName();
            switch (name) {
                case "name":
                    person.name = reader.nextString();
                    break;
                case "age":
                    person.age = reader.nextInt();
                    break;
                default:
                    break;
            }
            return person;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

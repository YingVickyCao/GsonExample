package com.hades.android.example.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hades.android.example.gson.ignore_field.StuHasExpose;
import com.hades.android.example.gson.ignore_field.StuHasTransient;
import com.hades.android.example.gson.rename_field.StuHasSerializedName;
import com.hades.android.example.gson.rename_field.StuHasSerializedName2;

import java.lang.reflect.Type;
import java.util.*;

public class Test {
    private static final String TAG = Test.class.getSimpleName();
    private Gson gson;

    public static void main(String[] args) {
        Test test = new Test();
        test.init();
//        test.all();
        test.current();
    }

    private void init() {
        gson = new Gson();
    }

    private void current() {
        collectionDeserialization();
    }

    private void all() {
        baseSerialization();
        baseDeserialization();

        arraySerialization();
        arrayDerialization();

        customSerialization();
        customDeserialization();

        collectionSerialization();
        collectionDeserialization();

        genericTypes_Serialization_Deserialization();

        serializedNameSerialization();
        serializedNameDeserialization();
        serializedNameSerialization2();
        serializedNameDeserialization2();

        expose();
        transientCustomSerialization();
        transientCustomDeserialization();
    }

    /**
     * primitive type -> json string
     */
    public void baseSerialization() {
        Log.d(TAG, gson.toJson(1));     // 1
        Log.d(TAG, gson.toJson("A"));   // "A
        Log.d(TAG, gson.toJson(10L));   // 10
        Log.d(TAG, gson.toJson(new Date()));    // "Sep 5, 2018 22:57:10"
        Log.d(TAG, gson.toJson(new int[]{1, 2}));   // [1,2]
    }

    /**
     * json string -> primitive type
     */
    public void baseDeserialization() {
        int one = gson.fromJson("1", int.class);
        Log.d(TAG, one + "");   // 1

        Integer oneInteger = gson.fromJson("1", Integer.class);
        Log.d(TAG, oneInteger + "");    // 1

        Long oneLong = gson.fromJson("1", Long.class);
        Log.d(TAG, oneLong + "");  // 1

        Boolean falseBoolean = gson.fromJson("false", Boolean.class);
        Log.d(TAG, falseBoolean + "");  // false

        String str = gson.fromJson("\"abc\"", String.class);
        Log.d(TAG, str + ""); // abc

        String str2 = gson.fromJson("def", String.class);
        Log.d(TAG, str2 + ""); // def
    }

    /**
     * Array  -> json string
     */
    public void arraySerialization() {
        int[] ints = {1, 2, 3, 4, 5};
        String[] strings = {"abc", "def", "ghi"};

        String s1 = gson.toJson(ints);     // [1,2,3,4,5]
        String s2 = gson.toJson(strings);  // ["abc", "def", "ghi"]

        Log.d(TAG, s1);
        Log.d(TAG, s2);
    }

    /**
     * json string -> array
     */
    public void arrayDerialization() {
        String jsonArray = "[1,2,3,4,5]";

        // Deserialization
        int[] ints2 = gson.fromJson(jsonArray, int[].class);
        System.out.println(Arrays.toString(ints2));
    }

    /**
     * bean -> json string
     */
    public void customSerialization() {
        String json = gson.toJson(new Stu());
        Log.d(TAG, json); // => {"age":25,"firstName":"Catty","lastName":"Smith","sex":"male"}
    }

    /**
     * json string -> bean
     */
    public void customDeserialization() {
        String json = "{\"age\":100,\"firstName\":\"John\",\"lastName\":\"Smith\",\"sex\":\"male\"}";
        Log.d(TAG, gson.fromJson(json, Stu.class).toString()); // => {firstName='John', lastName='Smith', sex='male', age=100}

        String json2 = "{age:100,firstName:John,lastName:Smith,sex:male}";
        Log.d(TAG, gson.fromJson(json2, Stu.class).toString()); // => {firstName='John', lastName='Smith', sex='male', age=100}
    }


    /**
     * Collection -> json string
     */
    public void collectionSerialization() {
        // Collection
        {
            Collection<Integer> ints = new ArrayList<>();
            ints.add(1);
            ints.add(2);
            ints.add(3);

            String json = gson.toJson(ints);  // [1,2,3]
            System.out.println(json);
        }

        // List
        {
            List<Integer> strings = new ArrayList<>();
            strings.add(1);
            strings.add(2);
            strings.add(3);
            String json = gson.toJson(strings);
            System.out.println(json); // [1,2,3]
        }
        // Set
        {
            Set<Integer> set = new HashSet<>();
            set.add(1);
            set.add(2);
            set.add(3);
            String json = gson.toJson(set);
            System.out.println(json);// [1,2,3]
        }

        // Map
        {
            HashMap<Integer, Integer> words = new HashMap<>();
            words.put(1, 11);
            words.put(2, 22);
            words.put(3, 33);
            String json = gson.toJson(words);
            System.out.println(json);// {"1":11,"2":22,"3":33}
        }
    }

    /**
     * json string -> Collection
     */
    public void collectionDeserialization() {
        // Collection
        {
            String json = "[1,2,3]";
            Type collectionType = new TypeToken<Collection<Integer>>() {
            }.getType();
            Collection<Integer> ints1 = gson.fromJson(json, collectionType);
            System.out.println(ints1.toString()); // [1, 2, 3]
        }

        // List
        {
            String json = "[1,2,3]";
            Type listType = new TypeToken<List<Integer>>() {
            }.getType();
            Collection<Integer> ints2 = gson.fromJson(json, listType);
            System.out.println(ints2); // [1, 2, 3]
        }
        // Set
        {
            String json = "[1,2,3]";
            Type listType = new TypeToken<Set<Integer>>() {
            }.getType();
            Collection<Integer> ints2 = gson.fromJson(json, listType);
            System.out.println(ints2); // [1, 2, 3]
        }

        // Map
        {
            String json = "{\"1\":11,\"2\":22,\"3\":33}";
            Type type = new TypeToken<HashMap<Integer, Integer>>() {
            }.getType();

            HashMap<Integer, Integer> map = gson.fromJson(json, type);
            System.out.println(map.toString()); // {1=11, 2=22, 3=33}
        }
    }

    /**
     * Generic Types
     */
    public void genericTypes_Serialization_Deserialization() {
        Foo<Integer> foo12 = new Foo<>(1024);
        // Generic Types -> json string
        String json = gson.toJson(foo12);
        Log.d(TAG, "genericTypes: " + json); // {"value":1024}
        //  json string -> Generic Types
        Log.d(TAG, "genericTypes: " + gson.fromJson(json, foo12.getClass()));   // {value=1024.0}

        Foo<Stu> foo13 = new Foo<>(new Stu());
        json = gson.toJson(foo13);
        Log.d(TAG, "genericTypes: " + json);    // {"value":{"firstName":"Catty","lastName":"Smith","sex":"male","age":25}}
        Log.d(TAG, "genericTypes: " + gson.fromJson(json, foo13.getClass()));   // {value={firstName=Catty, lastName=Smith, sex=male, age=25.0}}
    }

    /**
     * 属性重命名 @SerializedName 注解
     * Actual: {"name":"A", "age":24, "email_address":"ABC@example.com" }
     * Expect: {"name":"A", "age":24, "emailAddress":"ABC@example.com" }
     */
    private void serializedNameSerialization() {
        StuHasSerializedName stu = new StuHasSerializedName("A", 24, "ABC@example.com");
        String json = gson.toJson(stu);

        Log.d(TAG, json); // => {"age":24,"emailAddress":"ABC@example.com","name":"A"}
    }

    private void serializedNameDeserialization() {
        String json = "{\"age\":24,\"emailAddress\":\"ABC@example.com\",\"name\":\"A\"}";
        // ERROR:com.google.gson.JsonSyntaxException: com.google.gson.stream.MalformedJsonException: Expected name at line 1 column 2 path
        Log.d(TAG, gson.fromJson(json, StuHasSerializedName.class).toString());
    }


    private void serializedNameSerialization2() {
        StuHasSerializedName2 stu = new StuHasSerializedName2("A", 24, "ABC@example.com");
        String json = gson.toJson(stu);

        Log.d(TAG, json); // => {"name":"A","age":24,"emailAddress":"ABC@example.com"}
    }

    private void serializedNameDeserialization2() {
        String json = "{\"age\":24,\"email_address\":\"ABC@example.com\",\"email\":\"ABC@example.com\",\"name\":\"A\"}";
        StuHasSerializedName2 stu = gson.fromJson(json, StuHasSerializedName2.class);
        Log.d(TAG, stu.toString()); // {name='A', age=24, email='ABC@example.com'}
    }

    /**
     * @Expose:忽略某个字段
     */
    private void expose() {
        // If gson = new Gson(), @Expose not work.
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        StuHasExpose bean = new StuHasExpose("A", "ABC@example.com", 15, true);
        System.out.println(bean.toString());    // {name='A', email='ABC@example.com', age=15, isDeveloper=true}

        String json = gson.toJson(bean);
        System.out.println(json);   // {"name":"A","isDeveloper":true}

        String json2 = "{\"name\":\"A\",\"email\":\"ABC@example.com\",\"age\":15,\"isDeveloper\":true}";
        StuHasExpose bean2 = gson.fromJson(json2, StuHasExpose.class);
        System.out.println(bean2.toString());   // {name='A', email='null', age=15, isDeveloper=false}
    }

    /**
     * transient: 忽略某个字段
     * transient bean -> json string
     */
    // TODO:顺序？
    public void transientCustomSerialization() {
        String json = gson.toJson(new StuHasTransient("Hello", "Smith", "male", (short) 25));
        Log.d(TAG, json); // => {"firstName":"Hello","lastName":"Smith","sex":"male"}, does not have transient age 25
    }

    /**
     * json string -> transient bean
     */
    public void transientCustomDeserialization() {
//        String json = "{\"age\":100,\"firstName\":\"John\",\"lastName\":\"Smith\",\"sex\":\"male\"}";
        String json = "{\"firstName\":\"John\",\"lastName\":\"Smith\",\"sex\":\"male\"}";
        Log.d(TAG, gson.fromJson(json, StuHasTransient.class).toString());
    }

    private void check_null() {
        User user = new User(null, "ABC@example.com", false, 27);
        String json = gson.toJson(user);
        System.out.println(json); // {"email":"ABC@example.com","isDeveloper":false,"age":27}

        String json2 = "{\"isDeveloper\": true, \"name\": \"C\" }";
        User user2 = gson.fromJson(json2, User.class);
        System.out.println(user2.toString()); // {name='C', email='null', isDeveloper=true, age=0}
    }
}
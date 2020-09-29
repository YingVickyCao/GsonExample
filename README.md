# Example Code of Gson

# 1 primitive type <-> json string

```java
// primitive type -> json string
gson.toJson(1)

// json string -> primitive type
 int one = gson.fromJson("1", int.class);
Log.d(TAG, one + "");   // 1

Integer oneInteger = gson.fromJson("1", Integer.class);
Log.d(TAG, oneInteger + "");    // 1
```

# 2 Array <-> json string

```java
// Array  -> json string
int[] ints = {1, 2, 3, 4, 5};
gson.toJson(ints);     // [1,2,3,4,5]


// json string -> array
String jsonArray = "[1,2,3,4,5]";
int[] ints2 = gson.fromJson(jsonArray, int[].class);
```

# 3 bean <-> json string

```java
// bean -> json string
gson.toJson(new Stu())

// json string -> bean
String json = "{\"age\":100,\"firstName\":\"John\",\"lastName\":\"Smith\",\"sex\":\"male\"}";
gson.fromJson(json, Stu.class)
```

# 4 Collection （Generic Types） <-> json string

```java
// Collection -> json string
Collection<Integer> ints = new ArrayList<>();
ints.add(1);
ints.add(2);
gson.toJson(ints); // [1,2]

// json string -> Collection
 String json = "[1,2,3,4,5]";
// Deserialization
Type collectionType = new TypeToken<Collection<Integer>>() {
}.getType();
Collection<Integer> ints2 = gson.fromJson(json, collectionType);

```

```java
Foo<Integer> foo12 = new Foo<>(1024);
// Generic Types -> json string
String json = gson.toJson(foo12);
Log.d(TAG, "genericTypes: " + json); // {"value":1024}

//  json string -> Generic Types
Log.d(TAG, "genericTypes: " + gson.fromJson(json, foo12.getClass()));   // {value=1024.0}
```

# 5 属性重命名 `@SerializedName`

用@SerializedName 改变字段的命名

```java
@SerializedName("emailAddress")   // in json string
private String email_address;     // in bean
```

```java
// 三个属性(email、email_address、emailAddress)都中出现任意一个时均可以得到正确的结果. 当多种情况同时出时，以最后一个出现的值为准
@SerializedName(value = "emailAddress", alternate = {"email", "email_address"})
private String email;

String json = "{\"age\":24,\"email_address\":\"ABC@example.com\",\"email\":\"ABC@example.com\",\"name\":\"A\"}";
StuHasSerializedName2 stu = gson.fromJson(json, StuHasSerializedName2.class);
Log.d(TAG, stu.toString()); // {name='A', age=24, email='ABC@example.com'}
```

# 7 (反）序列化中如何忽略一个属性？

- `@Expose`

```java
@Expose
@Expose(deserialize = true,serialize = true) //序列化和反序列化都都生效，等价于上一条
@Expose(deserialize = true,serialize = false) //反序列化时生效
@Expose(deserialize = false,serialize = true) //序列化时生效
@Expose(deserialize = false,serialize = false) // 和不写注解一样
```

```java
public class StuHasExpose {
    @Expose()
    String name; // equals serialize & deserialize

    @Expose(serialize = false, deserialize = false)
    String email; // equals neither serialize nor deserialize

    @Expose(serialize = false)
    int age; // equals only deserialize

    @Expose(deserialize = false)
    boolean isDeveloper; // equals only serialize
}
```

如果所有的字段都被转化，不用添加@Expose。

- `transient`  
  序列化和反序列化都不包含该 field

```java
public class StuHasTransient {
    public String firstName;
    public String lastName;
    public String sex;
    public transient short age; // will not be serialized or deserialized
}
```

# 7 GsonBuilder

When:以非标准的方式使用 Gson,使用 GsonBuilder 定制 Gson 部分模块

# Refs

- https://www.jianshu.com/nb/4911520
- [jsonschema2pojo](http://www.jsonschema2pojo.org/)

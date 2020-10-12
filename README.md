# Example Code of Gson

| 序列化        | 反序列化        |
| ------------- | --------------- |
| gson.toJson() | gson.fromJson() |

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

# 2 bean <-> json string

```java
// bean -> json string
gson.toJson(new Stu())

// json string -> bean
String json = "{\"age\":100,\"firstName\":\"John\",\"lastName\":\"Smith\",\"sex\":\"male\"}";
gson.fromJson(json, Stu.class)
```

# 3 Array <-> json string

Gson 解析 jsonArray 时，有两种方式：数组，List。

```java
// Array  -> json string
int[] ints = {1, 2, 3, 4, 5};
gson.toJson(ints);     // [1,2,3,4,5]


// json string -> array
String jsonArray = "[1,2,3,4,5]";
int[] ints2 = gson.fromJson(jsonArray, int[].class);
```

# 4 Generic Types

## Generic Types for Collection(e.g.,List) <->json string

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

## Generic Types for POJO 类 <->json string

```java
Foo<Integer> foo12 = new Foo<>(1024);
// Generic Types -> json string
String json = gson.toJson(foo12);
Log.d(TAG, "genericTypes: " + json); // {"value":1024}

//  json string -> Generic Types
Log.d(TAG, "genericTypes: " + gson.fromJson(json, foo12.getClass()));   // {value=1024.0}
```

# 6 POJO 类 属性重命名 `@SerializedName`

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

# 7 序列化 和 反序列化中如何忽略一个属性？

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

# 8 GsonBuilder

- When:以非标准的方式使用 Gson,使用 GsonBuilder 定制 Gson 部分模块
- 命名策略(FieldNamingPolicy)  
  FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES:  
  会先根据大小写字符拆分属性名称，再用小写替换掉大写，替换掉的字母以\_开头

  FieldNamingPolicy.LOWER_CASE_WITH_DASHES:  
   会先根据大小写字符拆分属性名称，再用小写替换掉大写，替换掉的字母以-开头

  FieldNamingPolicy.UPPER_CAMEL_CASE:  
  第一个字母大写

  FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES:  
   两个字段，变成了大写字母开头，并以空格隔开

- 当 Json string 符合命名策略，即使名字不一样，Gson 也会执行映射，得到 bean。

# 9 Null

序列化时， Gson 不会为 null 值创建任何 JSON 数据。
反序列化时，json string 中缺少字段，bean 中该字段为默认值。

# 10 Nested Class

# 11 手动序列化和反序列化

- 序列化
  JsonWriter
- 反序列化  
  JsonParser  
  JsonReader

# Refs

- https://www.jianshu.com/nb/4911520
- [jsonschema2pojo](http://www.jsonschema2pojo.org/)
- https://www.cnblogs.com/baiqiantao/p/7512336.html

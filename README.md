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

- If field is transient, 序列化和反序列化都不包含该 field

# 4 Collection <-> json string

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

# 5 Generic Types <-> json string

```java
Foo<Integer> foo12 = new Foo<>(1024);
// Generic Types -> json string
String json = gson.toJson(foo12);
Log.d(TAG, "genericTypes: " + json); // {"value":1024}

//  json string -> Generic Types
Log.d(TAG, "genericTypes: " + gson.fromJson(json, foo12.getClass()));   // {value=1024.0}
```

# 6 属性重命名 @SerializedName 注解

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

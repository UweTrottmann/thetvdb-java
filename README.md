**Note: No plan for further updates, e.g. to support API v4. If you want to maintain this repo, let me know!**

# thetvdb-java

[TheTVDB.com](https://www.thetvdb.com) REST API wrapper written in Java using retrofit.

Last tested to work with API version: [`3.0.0`](https://api.thetvdb.com/swagger)

[Supported endpoints](https://github.com/UweTrottmann/thetvdb-java/issues/1)

## Usage
<a href="https://search.maven.org/search?q=thetvdb-java">Available on Maven Central</a>

Get via Gradle:
```groovy
implementation 'com.uwetrottmann.thetvdb-java:thetvdb-java:2.4.0'
```

Or Maven:
```xml
<dependency>
    <groupId>com.uwetrottmann.thetvdb-java</groupId>
    <artifactId>thetvdb-java</artifactId>
    <version>2.4.0</version>
</dependency>
```

Use like any other [retrofit](https://square.github.io/retrofit) based service.
Automatically gets a JSON web token so you only need to supply your API key.
For example:

```java
TheTvdb theTvdb = new TheTvdb(API_KEY);
try {
    Response<SeriesResponse> response = theTvdb.series()
        .series(83462, "en")
        .execute();
    if (response.isSuccessful()) {
        Series series = response.body().data;
        System.out.println(series.seriesName + " is awesome!");
    }
} catch (Exception e) {
    // see execute() javadoc 
}
```

### Android
This library ships Java 8 bytecode. This requires Android Gradle Plugin 3.2.x or newer.

## Use Proguard, R8!
It is likely not every method in this library is used, so it is probably useful to strip unused ones with Proguard.
Apply the [Proguard rules for retrofit](https://square.github.io/retrofit/#download).

Due to R8 being very eager in stripping unused fields even if they are set by a constructor (like `LoginData`), 
prevent entities from getting optimized. Obviously they also should not be obfuscated.
```proguard
-keep class com.uwetrottmann.thetvdb.entities.** { *; }
```

## License
This work by [Uwe Trottmann](https://uwetrottmann.com) is licensed under the [Apache License 2.0](LICENSE.txt).

[Contributors](https://github.com/UweTrottmann/thetvdb-java/graphs/contributors) and changes are tracked by Git.

Do not just copy, make it better.

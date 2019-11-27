**Pull requests (for example to support more API endpoints, bug fixes) are welcome!**

# thetvdb-java

[TheTVDB.com](https://www.thetvdb.com) REST API wrapper written in Java using retrofit.

Last tested to work with API version: [`3.0.0`](https://api.thetvdb.com/swagger)

[Supported endpoints](https://github.com/UweTrottmann/thetvdb-java/issues/1)

## Usage
<a href="https://search.maven.org/search?q=thetvdb-java">Available on Maven Central</a>

Get via Gradle:
```groovy
implementation 'com.uwetrottmann.thetvdb-java:thetvdb-java:2.1.0'
```

Or Maven:
```xml
<dependency>
    <groupId>com.uwetrottmann.thetvdb-java</groupId>
    <artifactId>thetvdb-java</artifactId>
    <version>2.1.0</version>
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

## Use Proguard!
You likely will not use every method in this library, so it is probably useful to strip unused ones with Proguard.
Just apply the [Proguard rules for retrofit](https://square.github.io/retrofit/#download).

## License
Created by [Uwe Trottmann](https://uwetrottmann.com).

See full [list of contributors](https://github.com/UweTrottmann/thetvdb-java/graphs/contributors).

Except where noted otherwise, released into the [public domain](UNLICENSE).
Do not just copy, make it better.

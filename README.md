**Pull requests (for example to support more API endpoints, bug fixes) are welcome!**

# thetvdb-java

<a href="https://search.maven.org/#search%7Cga%7C1%7Cthetvdb-java"><img src="https://img.shields.io/maven-central/v/com.uwetrottmann.thetvdb-java/thetvdb-java.svg?style=flat-square"></a>

The TVDB API wrapper in Java written using retrofit.

Currently supported [The TVDB API](https://api.thetvdb.com/swagger) version: `2.1.1`.

[Supported endpoints](https://github.com/UweTrottmann/thetvdb-java/issues/1)

## Usage
Get via Gradle:
```groovy
compile 'com.uwetrottmann.thetvdb-java:thetvdb-java:1.3.0'
```

Or Maven:
```xml
<dependency>
    <groupId>com.uwetrottmann.thetvdb-java</groupId>
    <artifactId>thetvdb-java</artifactId>
    <version>1.3.0</version>
</dependency>
```

Use like any other retrofit2 based service. Automatically gets a JSON web token so you only need to supply your API key.
For example:

```java
TheTvdb theTvdb = new TheTvdb(API_KEY);
retrofit2.Response<SeriesResponse> response = getTheTvdb().series()
    .series(83462, "en")
    .execute();
if (response.isSuccessful()) {
    Series series = response.body().data;
    System.out.println(series.seriesName + " is awesome!");
}

```

## Use Proguard!
You likely will not use every method in this library, so it is probably useful to strip unused ones with Proguard.
Just apply the [Proguard rules for retrofit][4].

## License
Created by [Uwe Trottmann](http://uwetrottmann.com).
Except where noted otherwise, released into the [public domain](UNLICENSE).
Do not just copy, make it better.
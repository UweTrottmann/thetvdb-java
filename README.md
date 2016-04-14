# thetvdb-java

<a href="https://search.maven.org/#search%7Cga%7C1%7Cthetvdb-java"><img src="https://img.shields.io/maven-central/v/com.uwetrottmann.thetvdb-java/thetvdb-java.svg?style=flat-square"></a>

The TVDB API wrapper in Java written using retrofit.

Currently supported [The TVDB API](https://api.thetvdb.com/swagger) version: `2.0.0`.

Currently in development, many endpoints are still incomplete, pull requests are welcome.

## Usage
Get via Gradle:
```groovy
compile 'com.uwetrottmann.thetvdb-java:thetvdb-java:1.0.2'
```

Or Maven:
```xml
<dependency>
    <groupId>com.uwetrottmann.thetvdb-java</groupId>
    <artifactId>thetvdb-java</artifactId>
    <version>1.0.2</version>
</dependency>
```

Use like any other retrofit2 based service. Automatically gets a JSON web token so you only need to supply your API key.
For example:

```java
TheTvdb theTvdb = new TheTvdb(API_KEY);
retrofit2.Response<SeriesWrapper> response = getTheTvdb().series()
    .series(83462, "en")
    .execute();
if (response.isSuccessful()) {
    Series series = response.body().data;
    System.out.println(series.seriesName + " is awesome!");
}

```

## License

    Copyright 2016 Uwe Trottmann

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
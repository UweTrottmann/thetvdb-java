package com.uwetrottmann.thetvdb;

import com.uwetrottmann.thetvdb.entities.Series;

import static org.assertj.core.api.Assertions.assertThat;

public class TestData {

    public static final int SERIES_TVDB_ID = 83462;
    public static final String SERIES_NAME = "Castle";
    public static final String LANGUAGE = "en";

    public static void assertTestSeries(Series series) {
        assertThat(series.id).isEqualTo(SERIES_TVDB_ID);
        assertThat(series.seriesName).isEqualTo(SERIES_NAME);
        assertThat(series.imdbId).isEqualTo("tt1219024");
        assertThat(series.zap2itId).isEqualTo("EP01085588");
        assertThat(series.added).isEqualTo("2008-10-17 15:05:50");
    }

}

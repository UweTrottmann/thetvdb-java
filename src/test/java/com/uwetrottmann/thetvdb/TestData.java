package com.uwetrottmann.thetvdb;

import com.uwetrottmann.thetvdb.entities.BasicEpisode;
import com.uwetrottmann.thetvdb.entities.Series;

import static org.assertj.core.api.Assertions.assertThat;

public class TestData {

    public static final int SERIES_TVDB_ID = 83462;
    public static final int EPISODE_TVDB_ID = 398671;
    public static final String SERIES_NAME = "Castle (2009)";
    public static final String LANGUAGE_EN = "en";
    public static final int LANGUAGE_EN_ID = 7;

    public static void assertTestSeries(Series series) {
        assertThat(series.id).isEqualTo(SERIES_TVDB_ID);
        assertThat(series.seriesName).isEqualTo(SERIES_NAME);
        assertThat(series.imdbId).isEqualTo("tt1219024");
        assertThat(series.zap2itId).isEqualTo("EP01085588");
        assertThat(series.added).isEqualTo("2008-10-17 15:05:50");
    }

    public static void assertBasicEpisode(BasicEpisode basicEpisode) {
        assertThat(basicEpisode.id).isPositive();
        assertThat(basicEpisode.airedEpisodeNumber).isGreaterThanOrEqualTo(0);
        if (basicEpisode.absoluteNumber != null) {
            assertThat(basicEpisode.absoluteNumber).isGreaterThanOrEqualTo(0);
        }
        if (basicEpisode.dvdEpisodeNumber != null) {
            assertThat(basicEpisode.dvdEpisodeNumber).isGreaterThanOrEqualTo(0);
        }
        if (basicEpisode.dvdSeason != null) {
            assertThat(basicEpisode.dvdSeason).isGreaterThanOrEqualTo(0);
        }
        assertThat(basicEpisode.airedSeason).isGreaterThanOrEqualTo(0);
        if (basicEpisode.airedSeasonID != null) {
            assertThat(basicEpisode.airedSeasonID).isPositive();
        }
        if (basicEpisode.episodeName != null) {
            assertThat(basicEpisode.language.episodeName).isEqualTo(LANGUAGE_EN);
        }
        if (basicEpisode.overview != null) {
            assertThat(basicEpisode.language.overview).isEqualTo(LANGUAGE_EN);
        }
    }
}

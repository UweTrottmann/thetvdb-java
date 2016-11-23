package com.uwetrottmann.thetvdb;

import com.uwetrottmann.thetvdb.entities.Episode;
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

    public static void assertBasicEpisode(Episode episode) {
        assertThat(episode.id).isPositive();
        assertThat(episode.airedEpisodeNumber).isGreaterThanOrEqualTo(0);
        if (episode.absoluteNumber != null) {
            assertThat(episode.absoluteNumber).isGreaterThanOrEqualTo(0);
        }
        if (episode.dvdEpisodeNumber != null) {
            assertThat(episode.dvdEpisodeNumber).isGreaterThanOrEqualTo(0);
        }
        if (episode.dvdSeason != null) {
            assertThat(episode.dvdSeason).isGreaterThanOrEqualTo(0);
        }
        assertThat(episode.airedSeason).isGreaterThanOrEqualTo(0);
        if (episode.airedSeasonID != null) {
            assertThat(episode.airedSeasonID).isPositive();
        }
        if (episode.episodeName != null) {
            assertThat(episode.language.episodeName).isEqualTo(LANGUAGE_EN);
        }
        if (episode.overview != null) {
            assertThat(episode.language.overview).isEqualTo(LANGUAGE_EN);
        }
    }
}

package com.uwetrottmann.thetvdb;

import static com.google.common.truth.Truth.assertThat;

import com.uwetrottmann.thetvdb.entities.Episode;
import com.uwetrottmann.thetvdb.entities.Series;
import com.uwetrottmann.thetvdb.entities.UserRating;

public class TestData {

    public static final int SERIES_TVDB_ID = 83462;
    public static final int EPISODE_TVDB_ID = 398671;
    public static final String SERIES_NAME = "Castle (2009)";
    public static final String SERIES_SLUG = "castle-2009";
    public static final String LANGUAGE_EN = "en";
    public static final int LANGUAGE_EN_ID = 7;
    public static final String RATING_TYPE = UserRating.TYPE_SERIES;
    public static final int RATING_VALUE = 7;
    public static final String USER_NAME = "";		// TODO - create a universal test account for everyone? Currently this must match the user associated with BaseTestCase.API_KEY
    public static final String USER_PASS_KEY = "";	// Find this at: https://www.thetvdb.com/dashboard/account/editinfo

    // add show that has double episode DVD numbers
    public static final int SERIES_TVDB_ID_STARGATE = 72449;

    public static void assertTestSeries(Series series) {
        assertThat(series.id).isEqualTo(SERIES_TVDB_ID);
        assertThat(series.slug).isEqualTo(SERIES_SLUG);
        assertThat(series.seriesName).isEqualTo(SERIES_NAME);
        assertThat(series.imdbId).isEqualTo("tt1219024");
        assertThat(series.zap2itId).isEqualTo("EP01085588");
        assertThat(series.added).isEqualTo("2008-10-17 15:05:50");
        // Assert to catch changes to images.
        assertThat(series.image).isNull();
        assertThat(series.poster).matches("posters/.*\\.jpg");
        assertThat(series.banner).matches("graphical/.*\\.jpg");
        assertThat(series.fanart).matches("fanart/original/.*\\.jpg");
    }

    public static void assertBasicEpisode(Episode episode) {
        assertThat(episode.id).isAtLeast(1);
        assertThat(episode.airedEpisodeNumber).isAtLeast(0);
        if (episode.absoluteNumber != null) {
            assertThat(episode.absoluteNumber).isAtLeast(0);
        }
        if (episode.dvdEpisodeNumber != null) {
            assertThat(episode.dvdEpisodeNumber).isAtLeast(0);
        }
        if (episode.dvdSeason != null) {
            assertThat(episode.dvdSeason).isAtLeast(0);
        }
        assertThat(episode.airedSeason).isAtLeast(0);
        if (episode.airedSeasonID != null) {
            assertThat(episode.airedSeasonID).isAtLeast(1);
        }
        if (episode.episodeName != null) {
            assertThat(episode.language).isNotNull();
            assertThat(episode.language.episodeName).isEqualTo(LANGUAGE_EN);
        }
        if (episode.overview != null) {
            assertThat(episode.language).isNotNull();
            assertThat(episode.language.overview).isEqualTo(LANGUAGE_EN);
        }
        // Assert to catch changes to images.
        if (episode.filename != null) {
            assertThat(episode.filename).matches("episodes/" + episode.seriesId + "/.*\\.jpg");
        }
    }
}

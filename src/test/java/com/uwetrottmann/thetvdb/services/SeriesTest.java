package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.SeriesEpisodes;
import com.uwetrottmann.thetvdb.entities.SeriesEpisodesSummary;
import com.uwetrottmann.thetvdb.entities.SeriesEpisodesSummaryWrapper;
import com.uwetrottmann.thetvdb.entities.SeriesWrapper;
import okhttp3.Headers;
import org.junit.Test;
import retrofit2.Call;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SeriesTest extends BaseTestCase {

    @Test
    public void test_series() throws IOException {
        Call<SeriesWrapper> call = getTheTvdb().series().series(TestData.SERIES_TVDB_ID, TestData.LANGUAGE);
        SeriesWrapper wrapper = call.execute().body();
        TestData.assertTestSeries(wrapper.data);
    }

    @Test
    public void test_seriesHeader() throws IOException {
        Call<Void> call = getTheTvdb().series().seriesHeader(TestData.SERIES_TVDB_ID, TestData.LANGUAGE);
        Headers headers = call.execute().headers();
        assertThat(headers.get("Last-Modified")).isNotEmpty();
    }

    @Test
    public void test_episodes() throws IOException {
        Call<SeriesEpisodes> call = getTheTvdb().series().episodes(TestData.SERIES_TVDB_ID, 2, TestData.LANGUAGE);
        SeriesEpisodes seriesEpisodes = call.execute().body();
    }

    @Test
    public void test_episodesQuery() throws IOException {
        Call<SeriesEpisodes> call = getTheTvdb().series().episodesQuery(
                TestData.SERIES_TVDB_ID,
                null,
                1, // airedSeason
                null, null, null, null, null,
                TestData.LANGUAGE
        );
        SeriesEpisodes seriesEpisodes = call.execute().body();
    }

    @Test
    public void test_episodesSummary() throws IOException {
        Call<SeriesEpisodesSummaryWrapper> call = getTheTvdb().series().episodesSummary(TestData.SERIES_TVDB_ID);
        SeriesEpisodesSummaryWrapper wrapper = call.execute().body();
        SeriesEpisodesSummary episodesSummary = wrapper.data;
        assertThat(episodesSummary.airedSeasons).isNotEmpty();
        assertThat(episodesSummary.airedEpisodes).isPositive();
        assertThat(episodesSummary.dvdSeasons).isNotEmpty();
        assertThat(episodesSummary.dvdEpisodes).isPositive();
    }
}

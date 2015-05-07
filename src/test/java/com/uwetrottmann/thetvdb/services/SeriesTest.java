package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.SeriesEpisodes;
import com.uwetrottmann.thetvdb.entities.SeriesEpisodesSummary;
import com.uwetrottmann.thetvdb.entities.SeriesEpisodesSummaryWrapper;
import com.uwetrottmann.thetvdb.entities.SeriesWrapper;
import org.junit.Test;
import retrofit.client.Header;
import retrofit.client.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SeriesTest extends BaseTestCase {

    @Test
    public void test_series() {
        SeriesWrapper wrapper = getTheTvdb().series().series(TestData.SERIES_TVDB_ID, TestData.LANGUAGE);
        TestData.assertTestSeries(wrapper.data);
    }

    @Test
    public void test_seriesHeader() {
        Response response = getTheTvdb().series().seriesHeader(TestData.SERIES_TVDB_ID, TestData.LANGUAGE);
        List<Header> headers = response.getHeaders();
        for (Header header : headers) {
            if ("Last-Modified".equals(header.getName())) {
                assertThat(header.getValue()).isNotEmpty();
            }
        }
    }

    @Test
    public void test_episodes() {
        SeriesEpisodes seriesEpisodes = getTheTvdb().series().episodes(TestData.SERIES_TVDB_ID, 2, TestData.LANGUAGE);
    }

    @Test
    public void test_episodesQuery() {
        SeriesEpisodes seriesEpisodes = getTheTvdb().series().episodesQuery(
                TestData.SERIES_TVDB_ID,
                null,
                1, // airedSeason
                null, null, null, null, null,
                TestData.LANGUAGE
        );
    }

    @Test
    public void test_episodesSummary() {
        SeriesEpisodesSummaryWrapper wrapper = getTheTvdb().series().episodesSummary(TestData.SERIES_TVDB_ID);
        SeriesEpisodesSummary episodesSummary = wrapper.data;
        assertThat(episodesSummary.airedSeasons).isNotEmpty();
        assertThat(episodesSummary.airedEpisodes).isPositive();
        assertThat(episodesSummary.dvdSeasons).isNotEmpty();
        assertThat(episodesSummary.dvdEpisodes).isPositive();
    }
}

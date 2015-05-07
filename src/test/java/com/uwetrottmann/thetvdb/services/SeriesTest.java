package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.SeriesEpisodes;
import com.uwetrottmann.thetvdb.entities.SeriesWrapper;
import org.junit.Test;

public class SeriesTest extends BaseTestCase {

    @Test
    public void test_series() {
        SeriesWrapper wrapper = getTheTvdb().series().series(TestData.SERIES_TVDB_ID, TestData.LANGUAGE);
        TestData.assertTestSeries(wrapper.data);
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
}

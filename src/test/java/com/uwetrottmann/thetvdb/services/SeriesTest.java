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
        SeriesEpisodes seriesEpisodes = getTheTvdb().series().episodes(TestData.SERIES_TVDB_ID, TestData.LANGUAGE);
    }
}

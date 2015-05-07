package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.SeriesResultsWrapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest extends BaseTestCase{

    @Test
    public void test_series() {
        SeriesResultsWrapper wrapper = getTheTvdb().search().series(TestData.SERIES_NAME, null, null, TestData.LANGUAGE);
        assertThat(wrapper.data).isNotEmpty();
        assertThat(wrapper.data.size()).isPositive();
    }
}

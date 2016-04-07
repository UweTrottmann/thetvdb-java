package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.SearchParamsData;
import com.uwetrottmann.thetvdb.entities.Series;
import com.uwetrottmann.thetvdb.entities.SeriesResultsWrapper;
import org.junit.Test;
import retrofit2.Call;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest extends BaseTestCase {

    @Test
    public void test_series() throws IOException {
        Call<SeriesResultsWrapper> call = getTheTvdb().search().series(
                TestData.SERIES_NAME,
                null,
                null,
                TestData.LANGUAGE_EN
        );
        SeriesResultsWrapper results = call.execute().body();
        for (Series series : results.data) {
            assertThat(series.id).isPositive();
            assertThat(series.seriesName).isNotEmpty();
        }
    }

    @Test
    public void test_params() throws Exception {
        Call<SearchParamsData> call = getTheTvdb().search().params();
        SearchParamsData result = call.execute().body();
        for (String param : result.data.params) {
            assertThat(param).isNotEmpty();
        }
    }
}

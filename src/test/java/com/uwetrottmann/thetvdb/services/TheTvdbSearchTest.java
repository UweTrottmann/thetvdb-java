package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.SearchParamsResponse;
import com.uwetrottmann.thetvdb.entities.Series;
import com.uwetrottmann.thetvdb.entities.SeriesResultsResponse;
import org.junit.Test;
import retrofit2.Call;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TheTvdbSearchTest extends BaseTestCase {

    @Test
    public void test_series() throws IOException {
        Call<SeriesResultsResponse> call = getTheTvdb().search().series(
                TestData.SERIES_NAME,
                null, null, null,
                TestData.LANGUAGE_EN
        );
        SeriesResultsResponse results = executeCall(call);
        for (Series series : results.data) {
            assertThat(series.id).isPositive();
            assertThat(series.seriesName).isNotEmpty();
        }
    }

    @Test
    public void test_seriesBySlug() throws IOException {
        Call<SeriesResultsResponse> call = getTheTvdb().search().series(
                null, null, null,
                TestData.SERIES_SLUG,
                TestData.LANGUAGE_EN
        );
        SeriesResultsResponse results = executeCall(call);
        assertThat(results.data).hasSize(1);
        Series series = results.data.get(0);
        assertThat(series.id).isEqualTo(TestData.SERIES_TVDB_ID);
    }

    @Test
    public void test_params() throws Exception {
        Call<SearchParamsResponse> call = getTheTvdb().search().params();
        SearchParamsResponse result = executeCall(call);
        for (String param : result.data.params) {
            assertThat(param).isNotEmpty();
        }
    }
}

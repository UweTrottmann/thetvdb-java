package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
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
                TestData.LANGUAGE
        );
        SeriesResultsWrapper series = call.execute().body();
        assertThat(series.data).hasSize(1);
    }
}

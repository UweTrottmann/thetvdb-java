package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.BasicEpisode;
import com.uwetrottmann.thetvdb.entities.SeriesEpisodes;
import com.uwetrottmann.thetvdb.entities.SeriesEpisodesSummary;
import com.uwetrottmann.thetvdb.entities.SeriesEpisodesSummaryWrapper;
import com.uwetrottmann.thetvdb.entities.SeriesImageQueryResults;
import com.uwetrottmann.thetvdb.entities.SeriesImagesQueryParams;
import com.uwetrottmann.thetvdb.entities.SeriesWrapper;
import okhttp3.Headers;
import org.junit.Test;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TheTvdbSeriesTest extends BaseTestCase {

    @Test
    public void test_series() throws IOException {
        Call<SeriesWrapper> call = getTheTvdb().series().series(TestData.SERIES_TVDB_ID, TestData.LANGUAGE_EN);
        SeriesWrapper wrapper = call.execute().body();
        TestData.assertTestSeries(wrapper.data);
    }

    @Test
    public void test_seriesHeader() throws IOException {
        Call<Void> call = getTheTvdb().series().seriesHeader(TestData.SERIES_TVDB_ID, TestData.LANGUAGE_EN);
        Headers headers = call.execute().headers();
        assertThat(headers.get("Last-Modified")).isNotEmpty();
    }

    @Test
    public void test_episodes() throws IOException {
        Integer page = 0;
        while (page != null) {
            Call<SeriesEpisodes> call = getTheTvdb().series().episodes(TestData.SERIES_TVDB_ID, page,
                    TestData.LANGUAGE_EN);
            SeriesEpisodes response = call.execute().body();

            assertEpisodes(response.data);

            page = response.links.next;
        }
    }

    @Test
    public void test_episodesQuery() throws IOException {
        Call<SeriesEpisodes> call = getTheTvdb().series().episodesQuery(
                TestData.SERIES_TVDB_ID,
                null,
                1, // airedSeason
                null, null, null, null, null,
                TestData.LANGUAGE_EN
        );
        SeriesEpisodes seriesEpisodes = call.execute().body();
        assertEpisodes(seriesEpisodes.data);
    }

    private static void assertEpisodes(List<BasicEpisode> episodes) {
        for (BasicEpisode basicEpisode : episodes) {
            TestData.assertBasicEpisode(basicEpisode);
        }
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

    @Test
    public void test_imagesQuery() throws Exception {
        String posterType = "poster";
        Call<SeriesImageQueryResults> call = getTheTvdb().series().imagesQuery(TestData.SERIES_TVDB_ID,
                posterType, null, null, null);
        SeriesImageQueryResults results = call.execute().body();
        for (SeriesImageQueryResults.SeriesImageQueryResult image : results.data) {
            assertThat(image.id).isPositive();
            assertThat(image.keyType).isEqualTo(posterType);
            assertThat(image.fileName).isNotEmpty();
            assertThat(image.resolution).isNotEmpty();
            assertThat(image.ratingsInfo.average).isBetween(0.0, 10.0);
            assertThat(image.thumbnail).isNotEmpty();
        }
    }

    @Test
    public void test_imagesQueryParams() throws IOException {
        Call<SeriesImagesQueryParams> call = getTheTvdb().series().imagesQueryParams(TestData.SERIES_TVDB_ID);
        SeriesImagesQueryParams body = call.execute().body();
        for (SeriesImagesQueryParams.SeriesImagesQueryParam queryParam : body.data) {
            assertThat(queryParam.keyType).isNotEmpty();
            for (String resolution : queryParam.resolution) {
                assertThat(resolution).isNotEmpty();
            }
            for (String subKey : queryParam.subKey) {
                assertThat(subKey).isNotEmpty();
            }
        }
    }
}

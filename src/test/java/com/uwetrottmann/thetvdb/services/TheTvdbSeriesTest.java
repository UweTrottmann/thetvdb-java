package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.Actor;
import com.uwetrottmann.thetvdb.entities.ActorsResponse;
import com.uwetrottmann.thetvdb.entities.Episode;
import com.uwetrottmann.thetvdb.entities.EpisodesResponse;
import com.uwetrottmann.thetvdb.entities.EpisodesSummary;
import com.uwetrottmann.thetvdb.entities.EpisodesSummaryResponse;
import com.uwetrottmann.thetvdb.entities.SeriesImageQueryResult;
import com.uwetrottmann.thetvdb.entities.SeriesImageQueryResultResponse;
import com.uwetrottmann.thetvdb.entities.SeriesImagesQueryParam;
import com.uwetrottmann.thetvdb.entities.SeriesImagesQueryParamResponse;
import com.uwetrottmann.thetvdb.entities.SeriesResponse;
import okhttp3.Headers;
import org.junit.Test;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TheTvdbSeriesTest extends BaseTestCase {

    @Test
    public void test_series() throws IOException {
        Call<SeriesResponse> call = getTheTvdb().series().series(TestData.SERIES_TVDB_ID, TestData.LANGUAGE_EN);
        SeriesResponse wrapper = call.execute().body();
        TestData.assertTestSeries(wrapper.data);
    }

    @Test
    public void test_seriesHeader() throws IOException {
        Call<Void> call = getTheTvdb().series().seriesHeader(TestData.SERIES_TVDB_ID, TestData.LANGUAGE_EN);
        Headers headers = call.execute().headers();
        assertThat(headers.get("Last-Modified")).isNotEmpty();
    }

    @Test
    public void test_actors() throws IOException {
        ActorsResponse response = executeCall(getTheTvdb().series().actors(TestData.SERIES_TVDB_ID));
        assertThat(response.data).isNotEmpty();
        for (Actor actor : response.data) {
            assertThat(actor.id).isPositive();
            assertThat(actor.seriesId).isEqualTo(TestData.SERIES_TVDB_ID);
            assertThat(actor.name).isNotEmpty();
        }
    }

    @Test
    public void test_episodes() throws IOException {
        Integer page = 0;
        while (page != null) {
            Call<EpisodesResponse> call = getTheTvdb().series().episodes(TestData.SERIES_TVDB_ID_STARGATE, page,
                    TestData.LANGUAGE_EN);
            EpisodesResponse response = call.execute().body();

            assertEpisodes(response.data);

            page = response.links.next;
        }
    }

    @Test
    public void test_episodesQuery() throws IOException {
        // search by aired season/episode
        Call<EpisodesResponse> call = getTheTvdb().series().episodesQuery(
                TestData.SERIES_TVDB_ID,
                null,
                1, // airedSeason
                null, null, null, null, null,null,
                TestData.LANGUAGE_EN
        );
        EpisodesResponse episodesResponse = call.execute().body();
        assertEpisodes(episodesResponse.data);

        // search by dvd season/episode
        call = getTheTvdb().series().episodesQuery(
                TestData.SERIES_TVDB_ID,
                null,
                null, null,
                1, null, null, null,null,
                TestData.LANGUAGE_EN
        );
        episodesResponse = call.execute().body();
        assertEpisodes(episodesResponse.data);

        // search by first aired date
        call = getTheTvdb().series().episodesQuery(
                TestData.SERIES_TVDB_ID,
                null,
                null,null,
                null, null, null, "2009-03-09",null,
                TestData.LANGUAGE_EN
        );
        episodesResponse = call.execute().body();
        assertEpisodes(episodesResponse.data);
    }

    private static void assertEpisodes(List<Episode> episodes) {
        for (Episode episode : episodes) {
            TestData.assertBasicEpisode(episode);
        }
    }

    @Test
    public void test_episodesSummary() throws IOException {
        Call<EpisodesSummaryResponse> call = getTheTvdb().series().episodesSummary(TestData.SERIES_TVDB_ID);
        EpisodesSummaryResponse wrapper = call.execute().body();
        EpisodesSummary episodesSummary = wrapper.data;
        assertThat(episodesSummary.airedSeasons).isNotEmpty();
        assertThat(episodesSummary.airedEpisodes).isPositive();
        assertThat(episodesSummary.dvdSeasons).isNotEmpty();
        assertThat(episodesSummary.dvdEpisodes).isPositive();
    }

    @Test
    public void test_imagesQuery() throws Exception {
        String posterType = "poster";
        Call<SeriesImageQueryResultResponse> call = getTheTvdb().series().imagesQuery(TestData.SERIES_TVDB_ID,
                posterType, null, null, null);
        SeriesImageQueryResultResponse results = call.execute().body();
        for (SeriesImageQueryResult image : results.data) {
            assertThat(image.id).isPositive();
            assertThat(image.keyType).isEqualTo(posterType);
            assertThat(image.fileName).isNotEmpty();
            assertThat(image.resolution).isNotEmpty();
            assertThat(image.ratingsInfo.average).isBetween(0.0, 10.0);
            assertThat(image.ratingsInfo.count).isGreaterThanOrEqualTo(0);
            assertThat(image.thumbnail).isNotEmpty();
        }
    }

    @Test
    public void test_imagesQueryParams() throws IOException {
        Call<SeriesImagesQueryParamResponse> call = getTheTvdb().series().imagesQueryParams(TestData.SERIES_TVDB_ID);
        SeriesImagesQueryParamResponse body = call.execute().body();
        for (SeriesImagesQueryParam queryParam : body.data) {
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

package com.uwetrottmann.thetvdb.services;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

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
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.Headers;
import org.junit.Test;
import retrofit2.Call;

public class TheTvdbSeriesTest extends BaseTestCase {

    @Test
    public void test_series() throws IOException {
        Call<SeriesResponse> call = getTheTvdb().series().series(TestData.SERIES_TVDB_ID, TestData.LANGUAGE_EN);
        SeriesResponse seriesResponse = executeCall(call);
        assertThat(seriesResponse.data).isNotNull();
        TestData.assertTestSeries(seriesResponse.data);
    }

    @Test
    public void test_seriesHeader() throws IOException {
        Call<Void> call = getTheTvdb().series().seriesHeader(TestData.SERIES_TVDB_ID, TestData.LANGUAGE_EN);
        Headers headers = executeVoidCall(call).headers();
        // No longer returned, regression?
//        assertThat(headers.get("Last-Modified")).isNotEmpty();
    }

    @Test
    public void test_actors() throws IOException {
        ActorsResponse response = executeCall(getTheTvdb().series().actors(TestData.SERIES_TVDB_ID));
        assertThat(response.data).isNotNull();
        assertThat(response.data).isNotEmpty();
        for (Actor actor : response.data) {
            assertThat(actor.id).isAtLeast(1);
            assertThat(actor.seriesId).isEqualTo(TestData.SERIES_TVDB_ID);
            assertThat(actor.name).isNotEmpty();
        }
    }

    @Test
    public void test_episodes() throws IOException {
        Integer page = 0;
        int episodeCount = 0;
        int pageCount = 0;
        while (page != null) {
            Call<EpisodesResponse> call = getTheTvdb().series().episodes(TestData.SERIES_TVDB_ID_STARGATE, page,
                    TestData.LANGUAGE_EN);
            EpisodesResponse response = executeCall(call);

            assertThat(response.data).isNotNull();
            assertEpisodes(response.data);
            episodeCount += response.data.size();

            pageCount++;
            assertThat(response.links).isNotNull();
            page = response.links.next;
        }
        // Assert this to be aware of API failures.
        assertThat(episodeCount).isEqualTo(222);
        // Assert this to be aware of page size changes.
        assertThat(pageCount).isEqualTo(3);
    }

    @Test
    public void test_episodesQuery() throws IOException {
        // search by aired season/episode
        Call<EpisodesResponse> call = getTheTvdb().series().episodesQuery(
                TestData.SERIES_TVDB_ID,
                null,
                1, // airedSeason
                null, null, null, null, null, null,
                TestData.LANGUAGE_EN
        );
        EpisodesResponse episodesResponse = executeCall(call);
        // Assert this to be aware of API failures.
        assertThat(episodesResponse.links).isNotNull();
        assertThat(episodesResponse.links.next).isNull();
        assertThat(episodesResponse.data).isNotNull();
        assertThat(episodesResponse.data.size()).isEqualTo(10);
        assertEpisodes(episodesResponse.data);

        // search by dvd season/episode
        call = getTheTvdb().series().episodesQuery(
                TestData.SERIES_TVDB_ID,
                null,
                null, null,
                1, null, null, null, null,
                TestData.LANGUAGE_EN
        );
        episodesResponse = executeCall(call);
        // Assert this to be aware of API failures.
        assertThat(episodesResponse.links).isNotNull();
        assertThat(episodesResponse.links.next).isNull();
        assertThat(episodesResponse.data).isNotNull();
        assertThat(episodesResponse.data.size()).isEqualTo(10);
        assertEpisodes(episodesResponse.data);

        // search by first aired date
        call = getTheTvdb().series().episodesQuery(
                TestData.SERIES_TVDB_ID,
                null,
                null, null,
                null, null, null, "2009-03-09", null,
                TestData.LANGUAGE_EN
        );
        episodesResponse = executeCall(call);
        // Assert this to be aware of API failures.
        assertThat(episodesResponse.links).isNotNull();
        assertThat(episodesResponse.links.next).isNull();
        assertThat(episodesResponse.data).isNotNull();
        assertThat(episodesResponse.data.size()).isEqualTo(1);
        assertEpisodes(episodesResponse.data);
    }

    private static void assertEpisodes(@Nullable List<Episode> episodes) {
        if (episodes == null) {
            fail("episodes == null");
        }
        for (Episode episode : episodes) {
            TestData.assertBasicEpisode(episode);
        }
    }

    @Test
    public void test_episodesSummary() throws IOException {
        Call<EpisodesSummaryResponse> call = getTheTvdb().series().episodesSummary(TestData.SERIES_TVDB_ID);
        EpisodesSummaryResponse wrapper = executeCall(call);
        EpisodesSummary episodesSummary = wrapper.data;
        assertThat(episodesSummary).isNotNull();
        assertThat(episodesSummary.airedSeasons).isNotEmpty();
        assertThat(episodesSummary.airedEpisodes).isAtLeast(1);
        assertThat(episodesSummary.dvdSeasons).isNotEmpty();
        assertThat(episodesSummary.dvdEpisodes).isAtLeast(1);
    }

    @Test
    public void test_imagesQuery() throws Exception {
        String posterType = "poster";
        String language = "en";
        Call<SeriesImageQueryResultResponse> call = getTheTvdb().series().imagesQuery(TestData.SERIES_TVDB_ID,
                posterType, null, null, language);
        SeriesImageQueryResultResponse results = executeCall(call);
        assertThat(results.data).isNotNull();
        for (SeriesImageQueryResult image : results.data) {
            assertThat(image.id).isAtLeast(1);
            assertThat(image.keyType).isEqualTo(posterType);
            assertThat(image.resolution).isNotEmpty();
            assertThat(image.ratingsInfo).isNotNull();
            assertThat(image.ratingsInfo.count).isAtLeast(0);
            //noinspection deprecation Keep average to notice if it is removed.
            assertThat(image.ratingsInfo.average).isEqualTo(image.ratingsInfo.count);
            assertThat(image.language).isEqualTo(language);
            // Assert to catch changes to images.
            assertThat(image.thumbnail).matches("posters/.*\\_t.jpg");
            assertThat(image.fileName).matches("posters/.*\\.jpg");
        }
    }

    @Test
    public void test_imagesQueryParams() throws IOException {
        Call<SeriesImagesQueryParamResponse> call = getTheTvdb().series().imagesQueryParams(TestData.SERIES_TVDB_ID);
        SeriesImagesQueryParamResponse body = executeCall(call);
        assertThat(body.data).isNotNull();
        for (SeriesImagesQueryParam queryParam : body.data) {
            assertThat(queryParam.keyType).isNotEmpty();
            assertThat(queryParam.resolution).isNotNull();
            for (String resolution : queryParam.resolution) {
                assertThat(resolution).isNotEmpty();
            }
            assertThat(queryParam.subKey).isNotNull();
            for (String subKey : queryParam.subKey) {
                assertThat(subKey).isNotEmpty();
            }
        }
    }
}

package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.Episode;
import com.uwetrottmann.thetvdb.entities.EpisodeData;
import org.junit.Test;
import retrofit2.Call;

import static org.assertj.core.api.Assertions.assertThat;

public class TheTvdbEpisodesTest extends BaseTestCase {
    @Test
    public void test_get() throws Exception {
        Call<EpisodeData> call = getTheTvdb().episodes().get(TestData.EPISODE_TVDB_ID, TestData.LANGUAGE_EN);
        EpisodeData episodeData = call.execute().body();
        Episode.FullEpisode episode = episodeData.data;
        TestData.assertBasicEpisode(episode);
        assertThat(episode.id).isEqualTo(TestData.EPISODE_TVDB_ID);
    }

}
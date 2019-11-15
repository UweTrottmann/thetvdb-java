package com.uwetrottmann.thetvdb.services;

import static com.google.common.truth.Truth.assertThat;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.Episode;
import com.uwetrottmann.thetvdb.entities.EpisodeResponse;
import org.junit.Test;

public class TheTvdbEpisodesTest extends BaseTestCase {
    @Test
    public void test_get() throws Exception {
        EpisodeResponse episodeResponse = executeCall(
                getTheTvdb().episodes().get(TestData.EPISODE_TVDB_ID, TestData.LANGUAGE_EN));
        Episode episode = episodeResponse.data;
        assertThat(episode).isNotNull();
        TestData.assertBasicEpisode(episode);
        assertThat(episode.id).isEqualTo(TestData.EPISODE_TVDB_ID);
    }

    @Test
    public void test_getInvalidLanguage() throws Exception {
        // Since API 3.0.0 this returns English instead of an error.
        // Reported to forums: https://forums.thetvdb.com/viewtopic.php?p=161690#p161690
        // Unsure if this is a regression or intended.
        EpisodeResponse episodeResponse = executeCall(getTheTvdb().episodes().get(TestData.EPISODE_TVDB_ID, "xx"));
//        assertThat(episodeResponse.errors.invalidLanguage).isNotEmpty();
        Episode episode = episodeResponse.data;
        assertThat(episode).isNotNull();
        TestData.assertBasicEpisode(episode);
        assertThat(episode.id).isEqualTo(TestData.EPISODE_TVDB_ID);
    }

}

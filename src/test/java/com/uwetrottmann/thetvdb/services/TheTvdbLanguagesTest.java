package com.uwetrottmann.thetvdb.services;

import static com.google.common.truth.Truth.assertThat;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.Language;
import com.uwetrottmann.thetvdb.entities.LanguageResponse;
import com.uwetrottmann.thetvdb.entities.LanguagesResponse;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import retrofit2.Call;

public class TheTvdbLanguagesTest extends BaseTestCase {

    @Test
    public void test_allAvailable() throws IOException {
        Call<LanguagesResponse> call = getTheTvdb().languages().allAvailable();
        LanguagesResponse response = executeCall(call);
        List<Language> languages = response.data;
        assertThat(languages).isNotNull();
        for (Language language : languages) {
            assertLanguage(language);
        }
    }

    @Test
    public void test_languageDetails() throws Exception {
        Call<LanguageResponse> call = getTheTvdb().languages().languageDetails(TestData.LANGUAGE_EN_ID);
        LanguageResponse response = executeCall(call);
        Language language = response.data;
        assertThat(language).isNotNull();
        assertLanguage(language);
        assertThat(language.id).isEqualTo(TestData.LANGUAGE_EN_ID);
    }

    private void assertLanguage(Language language) {
        assertThat(language.id).isAtLeast(1);
        // this will break should TheTVDB ever introduce region codes
        assertThat(language.abbreviation).hasLength(2);
        assertThat(language.name).isNotEmpty();
        assertThat(language.englishName).isNotEmpty();
    }

}

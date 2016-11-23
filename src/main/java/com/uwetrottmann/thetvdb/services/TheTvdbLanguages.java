package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.entities.LanguagesResponse;
import com.uwetrottmann.thetvdb.entities.LanguageResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TheTvdbLanguages {

    /**
     * All available languages. These language abbreviations can be used in the Accept-Language header for routes that
     * return translation records.
     */
    @GET("languages")
    Call<LanguagesResponse> allAvailable();

    /**
     * Information about a particular language, given the language ID.
     */
    @GET("languages/{id}")
    Call<LanguageResponse> languageDetails(@Path("id") int id);

}

package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.entities.LanguageData;
import com.uwetrottmann.thetvdb.entities.LanguageWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Languages {

    /**
     * All available languages. These language abbreviations can be used in the Accept-Language header for routes that
     * return translation records.
     */
    @GET("languages")
    Call<LanguageData> allAvailable();

    /**
     * Information about a particular language, given the language ID.
     */
    @GET("languages/{id}")
    Call<LanguageWrapper> languageDetails(@Path("id") int id);

}

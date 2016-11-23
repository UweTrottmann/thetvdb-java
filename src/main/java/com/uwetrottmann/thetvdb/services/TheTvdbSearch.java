package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.TheTvdb;
import com.uwetrottmann.thetvdb.entities.SearchParamsResponse;
import com.uwetrottmann.thetvdb.entities.SeriesResultsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TheTvdbSearch {

    /**
     * Allows the user to search for a series based on the following parameters.
     *
     * @param name Name of the series to search for.
     */
    @GET("search/series")
    Call<SeriesResultsResponse> series(
            @Query("name") String name,
            @Query("imdbId") String imdbId,
            @Query("zap2itId") String zap2itId,
            @Header(TheTvdb.HEADER_ACCEPT_LANGUAGE) String languages
    );

    @GET("search/series/params")
    Call<SearchParamsResponse> params();

}

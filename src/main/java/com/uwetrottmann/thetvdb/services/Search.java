package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.TheTvdb;
import com.uwetrottmann.thetvdb.entities.SeriesResultsWrapper;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

public interface Search {

    /**
     * Allows the user to search for a series based on the following parameters.
     *
     * @param name Name of the series to search for.
     */
    @GET("/search/series")
    SeriesResultsWrapper series(
            @Query("name") String name,
            @Query("imdbId") String imdbId,
            @Query("zap2itId") String zap2itId,
            @Header(TheTvdb.HEADER_ACCEPT_LANGUAGE) String languages

    );

}

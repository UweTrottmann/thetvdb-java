package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.TheTvdb;
import com.uwetrottmann.thetvdb.entities.SeriesWrapper;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

public interface Series {

    /**
     * Returns a series records that contains all information known about a particular series id.
     *
     * @param id ID of the series.
     * @param languages See <a href="http://en.wikipedia.org/wiki/Content_negotiation">Content negotiation</a>.
     */
    @GET("/series/{id}")
    SeriesWrapper series(@Path("id") int id, @Header(TheTvdb.HEADER_ACCEPT_LANGUAGE) String languages);

}

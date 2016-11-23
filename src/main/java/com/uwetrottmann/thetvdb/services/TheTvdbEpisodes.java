package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.TheTvdb;
import com.uwetrottmann.thetvdb.entities.EpisodeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TheTvdbEpisodes {

    @GET("episodes/{id}")
    Call<EpisodeResponse> get(
            @Path("id") int id,
            @Header(TheTvdb.HEADER_ACCEPT_LANGUAGE) String language
    );

}

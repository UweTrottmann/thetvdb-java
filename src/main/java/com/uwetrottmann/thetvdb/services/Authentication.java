package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.entities.LoginData;
import com.uwetrottmann.thetvdb.entities.Token;
import retrofit.http.Body;
import retrofit.http.POST;

public interface Authentication {

    /**
     * Returns a session token to be included in the rest of the requests. Note that API key authentication is required
     * for all subsequent requests and user auth is required for routes in the User section.
     */
    @POST("/login")
    Token login(@Body LoginData loginData);

}

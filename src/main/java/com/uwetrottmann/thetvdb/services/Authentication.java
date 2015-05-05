package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.entities.LoginData;
import com.uwetrottmann.thetvdb.entities.Token;
import retrofit.http.Body;
import retrofit.http.POST;

public interface Authentication {

    @POST("/login")
    Token login(@Body LoginData loginData);

}

package com.uwetrottmann.thetvdb;

import com.uwetrottmann.thetvdb.entities.LoginData;
import com.uwetrottmann.thetvdb.entities.Token;
import com.uwetrottmann.thetvdb.services.Authentication;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

import java.io.IOException;

public class TheTvdbAuthenticator implements Authenticator {

    public static final String PATH_LOGIN = "/" + Authentication.PATH_LOGIN;
    private TheTvdb theTvdb;

    public TheTvdbAuthenticator(TheTvdb theTvdb) {
        this.theTvdb = theTvdb;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        String path = response.request().url().encodedPath();
        if (PATH_LOGIN.equals(path)) {
            return null; // request was a login call and failed, give up.
        }
        if (responseCount(response) >= 2) {
            return null; // failed 2 times, give up.
        }

        // get a new json web token with the API key
        Call<Token> loginCall = theTvdb.authentication().login(new LoginData(theTvdb.apiKey()));
        retrofit2.Response<Token> loginResponse = loginCall.execute();
        if (!loginResponse.isSuccessful()) {
            return null; // failed to retrieve a token, give up.
        }

        String jsonWebToken = loginResponse.body().token;
        theTvdb.jsonWebToken(jsonWebToken);

        // retry request
        return response.request().newBuilder()
                .header(TheTvdb.HEADER_AUTHORIZATION, "Bearer" + " " + jsonWebToken)
                .build();
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

}

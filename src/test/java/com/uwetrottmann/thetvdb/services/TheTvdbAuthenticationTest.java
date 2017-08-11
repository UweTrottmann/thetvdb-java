package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.entities.LoginData;
import com.uwetrottmann.thetvdb.entities.Token;
import org.junit.Test;
import retrofit2.Call;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TheTvdbAuthenticationTest extends BaseTestCase {

    @Test
    public void test_login() throws IOException {
        if (API_KEY.length() == 0) {
            throw new IllegalArgumentException("Set your TheTVDB API key to test /login.");
        }

        // remove existing auth
        getTheTvdb().jsonWebToken(null);

        Call<Token> call = getTheTvdb().authentication().login(new LoginData(API_KEY));

        Token token = executeCall(call);
        System.out.println("Retrieved token: " + token.token + " (valid for 24 hours)");
    }

    @Test
    public void test_refreshToken() throws IOException {
        if (API_KEY.length() == 0) {
            throw new IllegalArgumentException("Set your TheTVDB API key to test /refresh_token.");
        }

        // remove existing auth
        getTheTvdb().jsonWebToken(null);

        // authenticate with API key
        Call<Token> call = getTheTvdb().authentication().login(new LoginData(API_KEY));
        Token token = executeCall(call);
        getTheTvdb().jsonWebToken(token.token);

        call = getTheTvdb().authentication().refreshToken();
        Token refreshedToken = executeCall(call);
        assertThat(refreshedToken.token).isNotEqualTo(token.token);
        System.out.println("Refreshed token: " + refreshedToken.token + " (valid for 24 hours)");
    }

}

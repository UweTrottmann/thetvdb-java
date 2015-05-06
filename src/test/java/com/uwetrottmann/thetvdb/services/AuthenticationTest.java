package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.entities.LoginData;
import com.uwetrottmann.thetvdb.entities.Token;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationTest extends BaseTestCase {

    private static final String API_KEY = "";

    @Test
    public void test_login() {
        if (API_KEY.length() == 0) {
            throw new IllegalArgumentException("Set your TheTVDB API key to test /login.");
        }

        // remove existing auth
        getTheTvdb().setJsonWebToken(null);

        Token token = getTheTvdb().authentication().login(new LoginData(API_KEY));
        System.out.println("Retrieved token: " + token.token + " (valid for 1 hour)");
    }

    @Test
    public void test_refreshToken() {
        if (API_KEY.length() == 0) {
            throw new IllegalArgumentException("Set your TheTVDB API key to test /refresh_token.");
        }

        // remove existing auth
        getTheTvdb().setJsonWebToken(null);

        // authenticate with API key
        Token token = getTheTvdb().authentication().login(new LoginData(API_KEY));
        getTheTvdb().setJsonWebToken(token.token);

        Token refreshedToken = getTheTvdb().authentication().refreshToken();
        assertThat(refreshedToken.token).isNotEqualTo(token.token);
        System.out.println("Refreshed token: " + refreshedToken.token + " (valid for 1 hour)");
    }

}

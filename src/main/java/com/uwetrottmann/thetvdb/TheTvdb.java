package com.uwetrottmann.thetvdb;

import com.google.gson.GsonBuilder;
import com.uwetrottmann.thetvdb.services.Authentication;
import com.uwetrottmann.thetvdb.services.Series;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class TheTvdb {

    public static final String API_URL = "https://api-dev.thetvdb.com";
    public static final String API_VERSION = "1.2.0";

    private static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    private static final String HEADER_AUTHORIZATION = "Authorization";

    private boolean isDebug;
    private RestAdapter restAdapter;
    private String jsonWebToken;

    /**
     * Create a new manager instance.
     */
    public TheTvdb() {
    }

    public TheTvdb setJsonWebToken(String value) {
        this.jsonWebToken = value;
        restAdapter = null;
        return this;
    }

    /**
     * Set the {@link retrofit.RestAdapter} log level.
     *
     * @param isDebug If true, the log level is set to {@link retrofit.RestAdapter.LogLevel#FULL}. Otherwise {@link
     * retrofit.RestAdapter.LogLevel#NONE}.
     */
    public TheTvdb setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
        if (restAdapter != null) {
            restAdapter.setLogLevel(isDebug ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE);
        }
        return this;
    }

    /**
     * Create a new {@link retrofit.RestAdapter.Builder}. Override this to e.g. set your own client or executor.
     *
     * @return A {@link retrofit.RestAdapter.Builder} with no modifications.
     */
    protected RestAdapter.Builder newRestAdapterBuilder() {
        return new RestAdapter.Builder();
    }

    /**
     * Return the current {@link retrofit.RestAdapter} instance. If none exists (first call, auth changed), builds a new
     * one.
     *
     * <p>When building, sets the endpoint and a {@link retrofit.RequestInterceptor} which supplies authentication
     * data.
     */
    protected RestAdapter getRestAdapter() {
        if (restAdapter == null) {
            RestAdapter.Builder builder = newRestAdapterBuilder();
            builder.setEndpoint(API_URL);
            builder.setConverter(new GsonConverter(new GsonBuilder().create()));
            builder.setRequestInterceptor(new RequestInterceptor() {
                public void intercept(RequestFacade request) {
                    request.addHeader(HEADER_ACCEPT, "application/vnd.thetvdb.v" + API_VERSION);
                    if (jsonWebToken != null && jsonWebToken.length() != 0) {
                        request.addHeader(HEADER_AUTHORIZATION, "Bearer" + " " + jsonWebToken);
                    }
                }
            });

            if (isDebug) {
                builder.setLogLevel(RestAdapter.LogLevel.FULL);
                builder.setClient(TheTvdbHelper.getDevelopmentOkClient());
            }

            restAdapter = builder.build();
        }

        return restAdapter;
    }

    /**
     * Obtaining and refreshing your JWT token.
     */
    public Authentication authentication() {
        return getRestAdapter().create(Authentication.class);
    }

    /**
     * Information about a specific series.
     */
    public Series series() {
        return getRestAdapter().create(Series.class);
    }

}

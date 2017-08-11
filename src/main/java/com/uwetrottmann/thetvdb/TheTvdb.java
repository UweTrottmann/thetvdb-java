package com.uwetrottmann.thetvdb;

import com.uwetrottmann.thetvdb.services.TheTvdbAuthentication;
import com.uwetrottmann.thetvdb.services.TheTvdbEpisodes;
import com.uwetrottmann.thetvdb.services.TheTvdbLanguages;
import com.uwetrottmann.thetvdb.services.TheTvdbSearch;
import com.uwetrottmann.thetvdb.services.TheTvdbSeries;
import com.uwetrottmann.thetvdb.services.TheTvdbUpdated;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public class TheTvdb {

    public static final String API_HOST = "api.thetvdb.com";
    public static final String API_URL = "https://" + API_HOST + "/";
    public static final String API_VERSION = "2.1.0";

    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    private String apiKey;
    private String currentJsonWebToken;

    /**
     * Create a new manager instance.
     */
    public TheTvdb(String apiKey) {
        this.apiKey = apiKey;
    }

    @Nullable
    public String apiKey() {
        return apiKey;
    }

    public void apiKey(@Nullable String apiKey) {
        this.apiKey = apiKey;
    }

    @Nullable
    public String jsonWebToken() {
        return currentJsonWebToken;
    }

    public void jsonWebToken(@Nullable String value) {
        this.currentJsonWebToken = value;
    }

    /**
     * Creates a {@link Retrofit.Builder} that sets the base URL, adds a Gson converter and sets {@link
     * #okHttpClient()} as its client.
     *
     * @see #okHttpClient()
     */
    protected Retrofit.Builder retrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient());
    }

    /**
     * Returns the default OkHttp client instance. It is strongly recommended to override this and use your app
     * instance.
     *
     * @see #setOkHttpClientDefaults(OkHttpClient.Builder)
     */
    protected synchronized OkHttpClient okHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            setOkHttpClientDefaults(builder);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    /**
     * Adds a network interceptor to add version and auth headers, an authenticator to automatically login using the
     * API key and a network interceptor to log requests.
     */
    protected void setOkHttpClientDefaults(OkHttpClient.Builder builder) {
        builder.addNetworkInterceptor(new TheTvdbInterceptor(this))
                .authenticator(new TheTvdbAuthenticator(this));
    }

    /**
     * Return the {@link Retrofit} instance. If called for the first time builds the instance.
     */
    protected Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = retrofitBuilder().build();
        }
        return retrofit;
    }

    /**
     * Obtaining and refreshing your JWT token.
     */
    public TheTvdbAuthentication authentication() {
        return getRetrofit().create(TheTvdbAuthentication.class);
    }

    /**
     * Information about a specific episode.
     */
    public TheTvdbEpisodes episodes() {
        return getRetrofit().create(TheTvdbEpisodes.class);
    }

    /**
     * Available languages and information.
     */
    public TheTvdbLanguages languages() {
        return getRetrofit().create(TheTvdbLanguages.class);
    }

    /**
     * Information about a specific series.
     */
    public TheTvdbSeries series() {
        return getRetrofit().create(TheTvdbSeries.class);
    }

    /**
     * Search for a particular series.
     */
    public TheTvdbSearch search() {
        return getRetrofit().create(TheTvdbSearch.class);
    }

    /**
     * Retrieve series which were recently updated.
     */
    public TheTvdbUpdated updated() {
        return getRetrofit().create(TheTvdbUpdated.class);
    }

}

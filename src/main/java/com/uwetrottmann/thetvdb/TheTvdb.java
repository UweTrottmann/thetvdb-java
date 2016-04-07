package com.uwetrottmann.thetvdb;

import com.uwetrottmann.thetvdb.services.Authentication;
import com.uwetrottmann.thetvdb.services.Languages;
import com.uwetrottmann.thetvdb.services.Search;
import com.uwetrottmann.thetvdb.services.Series;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("WeakerAccess")
public class TheTvdb {

    public static final String API_URL = "https://api.thetvdb.com/";
    public static final String API_VERSION = "2.0.0";

    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private HttpLoggingInterceptor logging;

    private boolean enableDebugLogging;

    private String apiKey;
    private String currentJsonWebToken;

    /**
     * Create a new manager instance.
     */
    public TheTvdb(String apiKey) {
        this.apiKey = apiKey;
    }

    public String apiKey() {
        return apiKey;
    }

    public void apiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String jsonWebToken() {
        return currentJsonWebToken;
    }

    public void jsonWebToken(String value) {
        this.currentJsonWebToken = value;
    }

    /**
     * Enable debug log output.
     *
     * @param enable If true, the log level is set to {@link HttpLoggingInterceptor.Level#BODY}. Otherwise {@link
     * HttpLoggingInterceptor.Level#NONE}.
     */
    public TheTvdb enableDebugLogging(boolean enable) {
        this.enableDebugLogging = enable;
        if (logging != null) {
            logging.setLevel(enable ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        }
        return this;
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
        if (enableDebugLogging) {
            logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
    }

    /**
     * Return the {@link Retrofit} instance. If called for the first time builds the instance, so if desired make sure
     * to call {@link #enableDebugLogging(boolean)} before.
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
    public Authentication authentication() {
        return getRetrofit().create(Authentication.class);
    }

    /**
     * Available languages and information.
     */
    public Languages languages() {
        return getRetrofit().create(Languages.class);
    }

    /**
     * Information about a specific series.
     */
    public Series series() {
        return getRetrofit().create(Series.class);
    }

    /**
     * Search for a particular series.
     */
    public Search search() {
        return getRetrofit().create(Search.class);
    }

}

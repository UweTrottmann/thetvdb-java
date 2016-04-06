package com.uwetrottmann.thetvdb;

import com.uwetrottmann.thetvdb.services.Authentication;
import com.uwetrottmann.thetvdb.services.Search;
import com.uwetrottmann.thetvdb.services.Series;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class TheTvdb {

    public static final String API_URL = "https://api.thetvdb.com";
    public static final String API_VERSION = "2.0.0";

    private static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    private static final String HEADER_AUTHORIZATION = "Authorization";

    private Retrofit retrofit;
    private HttpLoggingInterceptor logging;

    private boolean isDebug;
    private String jsonWebToken;

    /**
     * Create a new manager instance.
     */
    public TheTvdb() {
    }

    public TheTvdb setJsonWebToken(String value) {
        this.jsonWebToken = value;
        retrofit = null;
        return this;
    }

    /**
     * Set the default logging interceptors log level.
     *
     * @param isDebug If true, the log level is set to {@link HttpLoggingInterceptor.Level#BODY}. Otherwise {@link
     * HttpLoggingInterceptor.Level#NONE}.
     * @see #okHttpClientBuilder()
     */
    public TheTvdb setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
        if (logging != null) {
            logging.setLevel(isDebug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        }
        return this;
    }

    /**
     * Creates a {@link Retrofit.Builder} that sets the base URL, adds a Gson converter and sets {@link
     * #okHttpClientBuilder()} as its client.
     * <p>
     * Override this to for example set your own call executor.
     *
     * @see #okHttpClientBuilder()
     */
    protected Retrofit.Builder retrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder().build());
    }

    /**
     * Creates a {@link OkHttpClient.Builder} for usage with {@link #retrofitBuilder()}. Adds interceptors to add auth
     * headers and to log requests.
     * <p>
     * Override this to for example add your own interceptors.
     *
     * @see #retrofitBuilder()
     */
    protected OkHttpClient.Builder okHttpClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.header(HEADER_ACCEPT, "application/vnd.thetvdb.v" + API_VERSION);
                if (jsonWebToken != null && jsonWebToken.length() != 0) {
                    builder.header(HEADER_AUTHORIZATION, "Bearer" + " " + jsonWebToken);
                }
                return chain.proceed(builder.build());
            }
        });
        if (isDebug) {
            logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        return builder;
    }

    /**
     * Return the current {@link Retrofit} instance. If none exists (first call, auth changed), builds a new one. <p/>
     * <p>When building, sets the base url and a custom client with an {@link Interceptor} which supplies
     * authentication
     * data.
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

package com.uwetrottmann.thetvdb;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class TheTvdbInterceptor implements Interceptor {

    private TheTvdb theTvdb;

    public TheTvdbInterceptor(TheTvdb theTvdb) {
        this.theTvdb = theTvdb;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return handleIntercept(chain, jsonWebToken());
    }

    /**
     * If the host matches {@link TheTvdb#API_HOST} adds an Accept header for the current {@link TheTvdb#API_VERSION}
     * and if not present an Authorization header using the given JSON web token.
     */
    public static Response handleIntercept(Chain chain, String jsonWebToken) throws IOException {
        Request request = chain.request();
        if (!TheTvdb.API_HOST.equals(request.url().host())) {
            // do not intercept requests for other hosts
            // this allows the interceptor to be used on a shared okhttp client
            return chain.proceed(request);
        }

        Request.Builder builder = request.newBuilder();

        // request specific API version
        builder.header(TheTvdb.HEADER_ACCEPT, "application/vnd.thetvdb.v" + TheTvdb.API_VERSION);

        // add authorization header
        if (hasNoAuthorizationHeader(request) && jsonWebTokenIsNotEmpty(jsonWebToken)) {
            builder.header(TheTvdb.HEADER_AUTHORIZATION, "Bearer" + " " + jsonWebToken);
        }
        return chain.proceed(builder.build());
    }

    public String jsonWebToken() {
        return theTvdb.jsonWebToken();
    }

    private static boolean hasNoAuthorizationHeader(Request request) {
        return request.header(TheTvdb.HEADER_AUTHORIZATION) == null;
    }

    private static boolean jsonWebTokenIsNotEmpty(String jsonWebToken) {
        return jsonWebToken != null && jsonWebToken.length() != 0;
    }

}

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
        if (hasNoAuthorizationHeader(request) && jsonWebTokenIsNotEmpty()) {
            builder.header(TheTvdb.HEADER_AUTHORIZATION, "Bearer" + " " + jsonWebToken());
        }
        return chain.proceed(builder.build());
    }

    private String jsonWebToken() {
        return theTvdb.jsonWebToken();
    }

    private boolean hasNoAuthorizationHeader(Request request) {
        return request.header(TheTvdb.HEADER_AUTHORIZATION) == null;
    }

    private boolean jsonWebTokenIsNotEmpty() {
        return jsonWebToken() != null && jsonWebToken().length() != 0;
    }

}

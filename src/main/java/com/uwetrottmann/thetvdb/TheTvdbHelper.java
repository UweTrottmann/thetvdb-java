package com.uwetrottmann.thetvdb;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import retrofit.client.OkClient;

import java.io.IOException;
import java.util.logging.Logger;

public class TheTvdbHelper {

    private static final Logger logger = Logger.getLogger(TheTvdbHelper.class.getName());

    private static OkClient okClient;

    /**
     * Do NOT use in production. This returns a modified {@link OkClient} that logs every request including headers.
     */
    public static synchronized OkClient getDevelopmentOkClient() {
        if (okClient == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    long t1 = System.nanoTime();
                    Request request = chain.request();
                    logger.info(String.format("Sending request %s on %s%n%s",
                            request.url(), chain.connection(), request.headers()));
                    Response response = chain.proceed(request);

                    long t2 = System.nanoTime();
                    logger.info(String.format("Received response for %s in %.1fms%n%s",
                            request.url(), (t2 - t1) / 1e6d, response.headers()));
                    return response;
                }
            });
            okClient = new OkClient(okHttpClient);
        }
        return okClient;
    }

}

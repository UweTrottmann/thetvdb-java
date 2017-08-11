package com.uwetrottmann.thetvdb;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.BeforeClass;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static org.assertj.core.api.Assertions.fail;

public abstract class BaseTestCase {

    /** <b>WARNING:</b> do not use this in your code. This is for testing purposes only. */
    protected static final String API_KEY = "0B0BB36928753CD8";

    private static final boolean DEBUG = true;

    private static final TestTheTvdb theTvdb = new TestTheTvdb(API_KEY);

    static class TestTheTvdb extends TheTvdb {

        public TestTheTvdb(String apiKey) {
            super(apiKey);
        }

        @Override
        protected void setOkHttpClientDefaults(OkHttpClient.Builder builder) {
            super.setOkHttpClientDefaults(builder);
            if (DEBUG) {
                // add logging
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String s) {
                        // standard output is easier to read
                        System.out.println(s);
                    }
                });
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }
        }
    }

    @BeforeClass
    public static void setUpOnce() {
        if (API_KEY.length() == 0) {
            throw new IllegalArgumentException("Set a valid value for API_KEY.");
        }
    }

    protected final TheTvdb getTheTvdb() {
        return theTvdb;
    }

    protected <T> T executeCall(Call<T> call) throws IOException {
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            T body = response.body();
            if (body == null) {
                fail("body == null");
            } else {
                return body;
            }
        } else {
            handleFailedResponse(response);
        }
        return null;
    }

    private static void handleFailedResponse(Response response) {
        if (response.code() == 401) {
            fail(String.format("Authorization required: %d %s", response.code(), response.message()));
        } else {
            fail(String.format("Request failed: %d %s", response.code(), response.message()));
        }
    }

}

package com.uwetrottmann.thetvdb;

import static org.junit.Assert.fail;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.BeforeClass;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

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
                // add logging, standard output is easier to read
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor(System.out::println);
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addNetworkInterceptor(logging);
            }
        }
    }

    @BeforeClass
    public static void setUpOnce() {
        //noinspection ConstantConditions
        if (API_KEY.length() == 0) {
            throw new IllegalArgumentException("Set a valid value for API_KEY.");
        }
    }

    protected final TheTvdb getTheTvdb() {
        return theTvdb;
    }

    /**
     * Execute call with non-Void response body.
     */
    protected <T> T executeCall(Call<T> call) throws IOException {
        Response<T> response = call.execute();
        if (!response.isSuccessful()) {
            handleFailedResponse(response);
        }
        T body = response.body();
        if (body != null) {
            return body;
        } else {
            throw new IllegalStateException("Body should not be null for successful response");
        }
    }

    /**
     * Execute call with Void response body.
     */
    protected <T> Response<T> executeVoidCall(Call<T> call) throws IOException {
        Response<T> response = call.execute();
        if (!response.isSuccessful()) {
            handleFailedResponse(response); // will throw error
        }
        return response;
    }
    
    /**
     * Execute call with expected failure
     */
    protected <T> Response<T> executeExpectedErrorCall(Call<T> call, int expectedErrorCode) throws IOException {
    	Response<T> response = call.execute();
    	if (response.code() != expectedErrorCode) {
    		fail(String.format("Expected error code %d but instead got %d %s", expectedErrorCode, response.code(), response.message()));
    	}
    	
    	return response;
    }

    private static void handleFailedResponse(Response response) {
        if (response.code() == 401) {
            fail(String.format("Authorization required: %d %s", response.code(), response.message()));
        } else {
            fail(String.format("Request failed: %d %s", response.code(), response.message()));
        }
    }

}

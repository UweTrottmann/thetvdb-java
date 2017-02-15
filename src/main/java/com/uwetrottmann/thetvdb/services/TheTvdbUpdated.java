package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.entities.SeriesUpdatesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheTvdbUpdated {

    /**
     * Returns an array of series that have changed in a maximum of one week blocks since the provided fromTime.
     * The user may specify a toTime to grab results for less than a week. Any timespan larger than a week will
     * be reduced down to one week automatically.
     *
     * @param fromTime Epoch time to start your date range.
     * @param toTime Epoch time to end your date range. Must be one week from fromTime
     */
    @GET("updated/query")
    Call<SeriesUpdatesResponse> seriesUpdates(@Query("fromTime") Long fromTime, @Query("toTime") Long toTime);
}

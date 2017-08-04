package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.TheTvdb;
import com.uwetrottmann.thetvdb.entities.ActorsResponse;
import com.uwetrottmann.thetvdb.entities.EpisodesResponse;
import com.uwetrottmann.thetvdb.entities.EpisodesSummaryResponse;
import com.uwetrottmann.thetvdb.entities.SeriesImageQueryResultResponse;
import com.uwetrottmann.thetvdb.entities.SeriesImagesQueryParamResponse;
import com.uwetrottmann.thetvdb.entities.SeriesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheTvdbSeries {

    /**
     * Returns a series records that contains all information known about a particular series id.
     *
     * <p>If a translation is not available null will be returned (such as for seriesName or overview). Also errors will
     * include '"invalidLanguage": "Incomplete or no translation for the given language"'.
     *
     * @param id ID of the series.
     * @param language See <a href="http://en.wikipedia.org/wiki/Content_negotiation">Content negotiation</a>.
     */
    @GET("series/{id}")
    Call<SeriesResponse> series(
            @Path("id") int id,
            @Header(TheTvdb.HEADER_ACCEPT_LANGUAGE) String language
    );

    @HEAD("series/{id}")
    Call<Void> seriesHeader(
            @Path("id") int id,
            @Header(TheTvdb.HEADER_ACCEPT_LANGUAGE) String language
    );

    /**
     * @see <a href="https://api.thetvdb.com/swagger#!/Series/get_series_id_actors">/series/{id}/actors</a>
     */
    @GET("series/{id}/actors")
    Call<ActorsResponse> actors(@Path("id") int id);

    /**
     * All episodes for a given series. Paginated with 100 results per page.
     *
     * <p>If a translation is not available null will be returned (such as for episodeName or overview) and the
     * associated language property will be ''. Also errors will include '"invalidLanguage": "Some translations were not
     * available in the specified language"'.
     *
     * @param id ID of the series.
     * @param language See <a href="http://en.wikipedia.org/wiki/Content_negotiation">Content negotiation</a>.
     * @param page Page of results to fetch. Defaults to page 1 if not provided.
     */
    @GET("series/{id}/episodes")
    Call<EpisodesResponse> episodes(
            @Path("id") int id,
            @Query("page") Integer page,
            @Header(TheTvdb.HEADER_ACCEPT_LANGUAGE) String language
    );

    /**
     * This route allows the user to query against episodes for the given series. The response is a paginated array of
     * episode records that have been filtered down to basic information.
     *
     * @param id ID of the series.
     * @param language See <a href="http://en.wikipedia.org/wiki/Content_negotiation">Content negotiation</a>. Records
     * are returned with the Episode name and Overview in the desired language, if it exists. If there is no
     * translation
     * for the given language, then the record is still returned but with empty values for the translated fields.
     * @param page Page of results to fetch. Defaults to page 1 if not provided.
     */
    @GET("series/{id}/episodes/query")
    Call<EpisodesResponse> episodesQuery(
            @Path("id") int id,
            @Query("absoluteNumber") Integer absoluteNumber,
            @Query("airedSeason") Integer airedSeason,
            @Query("airedEpisode") Integer airedEpisode,
            @Query("dvdSeason") Integer dvdSeason,
            @Query("dvdEpisode") Double dvdEpisode,
            @Query("imdbId") String imdbId,
            @Query("firstAired") String firstAired,
            @Query("page") Integer page,
            @Header(TheTvdb.HEADER_ACCEPT_LANGUAGE) String language
    );

    @GET("series/{id}/episodes/summary")
    Call<EpisodesSummaryResponse> episodesSummary(
            @Path("id") int id
    );

    @GET("series/{id}/images/query")
    Call<SeriesImageQueryResultResponse> imagesQuery(
            @Path("id") int id,
            @Query("keyType") String keyType,
            @Query("resolution") String resolution,
            @Query("subKey") String subKey,
            @Header(TheTvdb.HEADER_ACCEPT_LANGUAGE) String language
    );

    @GET("series/{id}/images/query/params")
    Call<SeriesImagesQueryParamResponse> imagesQueryParams(
            @Path("id") int id
    );

}

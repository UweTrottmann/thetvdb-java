package com.uwetrottmann.thetvdb.services;

import com.uwetrottmann.thetvdb.entities.UserFavoritesResponse;
import com.uwetrottmann.thetvdb.entities.UserRatingsQueryParamsRepsonse;
import com.uwetrottmann.thetvdb.entities.UserRatingsResponse;
import com.uwetrottmann.thetvdb.entities.UserResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheTvdbUser
{
	/**
	 * Returns basic information about the currently authenticated user.
	 */
	@GET("/user")
	Call<UserResponse> user();
	
	/**
	 * Returns an array of favorite series for a given user, will be a blank array if no favorites exist.
	 */
	@GET("/user/favorites")
	Call<UserFavoritesResponse> favorites();

	/**
	 * Deletes the given series ID from the user’s favorite’s list and returns the updated list.
	 */
	@DELETE("/user/favorites/{id}")
	Call<UserFavoritesResponse> deleteFavorite(
			@Path("id") long id
	);

	/**
	 * Adds the supplied series ID to the user’s favorite’s list and returns the updated list.
	 */
	@PUT("/user/favorites/{id}")
	Call<UserFavoritesResponse> addFavorite(
			@Path("id") long id
	);

	/**
	 * Returns an array of ratings for the given user.
	 */
	@GET("/user/ratings")
	Call<UserRatingsResponse> ratings();

	/**
	 * Returns an array of ratings for a given user that match the query.
	 */
	@GET("/user/ratings/query")
	Call<UserRatingsResponse> ratingsQuery(
			@Query("itemType") String itemType
	);
	
	/**
	 * Returns a list of query params for use in the /user/ratings/query route.
	 */
	@GET("/user/ratings/query/params")
	Call<UserRatingsQueryParamsRepsonse> ratingsQueryParams();

	/**
	 * This route deletes a given rating of a given type.
	 */
	@DELETE("/user/ratings/{itemType}/{itemId}")
	Call<UserRatingsResponse> deleteRating(
			@Path("itemType") String itemType,
			@Path("itemId") long itemId
	);
	
	/**
	 * This route updates a given rating of a given type.
	 */
	@PUT("/user/ratings/{itemType}/{itemId}/{itemRating}")
	Call<UserRatingsResponse> addRating(
			@Path("itemType") String itemType,
			@Path("itemId") long itemId,
			@Path("itemRating") long itemRating
	);
}

package com.uwetrottmann.thetvdb.services;

import static com.google.common.truth.Truth.assertThat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import org.junit.Test;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.LoginData;
import com.uwetrottmann.thetvdb.entities.Token;
import com.uwetrottmann.thetvdb.entities.UserFavoritesResponse;
import com.uwetrottmann.thetvdb.entities.UserRating;
import com.uwetrottmann.thetvdb.entities.UserRatingsQueryParamsRepsonse;
import com.uwetrottmann.thetvdb.entities.UserRatingsResponse;
import com.uwetrottmann.thetvdb.entities.UserResponse;

import retrofit2.Call;
import retrofit2.Response;

public class TheTvdbUserTest extends BaseTestCase
{
	protected void doUserLogin() throws IOException {
		doUserLogin(TestData.USER_NAME, TestData.USER_PASS_KEY, false);
	}
	
	protected void doUserLogin(String userName, String userPassKey, boolean expectUnauthorized) throws IOException {
		LoginData loginData = new LoginData(API_KEY);
		loginData.user(userName, userPassKey);
		Call<Token> call = getTheTvdb().authentication().login(loginData);
		if (expectUnauthorized) {
			Response<Token> errorResponse = executeExpectedErrorCall(call, HttpURLConnection.HTTP_UNAUTHORIZED);
			assertThat(errorResponse).isNotNull();
			assertThat(errorResponse.code()).isEqualTo(HttpURLConnection.HTTP_UNAUTHORIZED);
		} else {
			Token token = executeCall(call);
			assertThat(token).isNotNull();
			getTheTvdb().jsonWebToken(token.token);
		}
	}
	
	@Test
	public void test_badLogin() throws IOException {
		doUserLogin("nohbody", "itsaliteraryreference", true);
	}
	
	@Test
	public void test_goodLogin() throws IOException {
		doUserLogin();
	}
	
	@Test
	public void test_user() throws IOException {
		doUserLogin();
		Call<UserResponse> call = getTheTvdb().user().user();
		UserResponse response = executeCall(call);
		assertThat(response.data).isNotNull();
		assertThat(response.data.userName).isEqualTo(TestData.USER_NAME);
	}
	
	protected List<String> getFavorites() throws IOException {
		Call<UserFavoritesResponse> call = getTheTvdb().user().favorites();
		UserFavoritesResponse response = executeCall(call);
		assertThat(response.data).isNotNull();
		assertThat(response.data.favorites).isNotNull();
		return response.data.favorites;
	}
	
	protected void addFavorite(long seriesId, boolean expectConflict) throws IOException {
		Call<UserFavoritesResponse> call = getTheTvdb().user().addFavorite(seriesId);
		
		if (expectConflict) {
			Response<UserFavoritesResponse> errorResponse = executeExpectedErrorCall(call, HttpURLConnection.HTTP_CONFLICT);
			assertThat(errorResponse).isNotNull();
			assertThat(errorResponse.code()).isEqualTo(HttpURLConnection.HTTP_CONFLICT);
		} else {
			UserFavoritesResponse response = executeCall(call);
			assertThat(response.data).isNotNull();
			assertThat(response.data.favorites).isNotNull();
			assertThat(response.data.favorites).contains(String.valueOf(seriesId));
		}
	}
	
	protected void deleteFavorite(long seriesId, boolean expectConflict) throws IOException {
		Call<UserFavoritesResponse> call = getTheTvdb().user().deleteFavorite(seriesId);
		
		if (expectConflict) {
			Response<UserFavoritesResponse> errorResponse = executeExpectedErrorCall(call, HttpURLConnection.HTTP_CONFLICT);
			assertThat(errorResponse).isNotNull();
			assertThat(errorResponse.code()).isEqualTo(HttpURLConnection.HTTP_CONFLICT);
		} else {
			UserFavoritesResponse response = executeCall(call);
			assertThat(response.data).isNotNull();
			assertThat(response.data.favorites).isNotNull();
			// TODO	as of 2020-02-19 the v3 API for PUT/DELETE on /user/favorites/{id} returns before-update AND after-update lists
			// 		which means a successful DELETE will contain the series id in the response due to the inclusion of the "before" list
			//		Issue raised: https://forums.thetvdb.com/viewtopic.php?f=118&t=62738
			//		Upon resolution, this comment can be deleted and the following statement restored
//			assertThat(response.data.favorites).doesNotContain(String.valueOf(seriesId));
		}
	}
	
	@Test
	public void test_favoritesCombined() throws IOException {
		doUserLogin();
		
		// Get the current user favorites
		List<String> favorites = getFavorites();
		
		Integer seriesId = null;
		boolean addFirst = false;
		
		// In order to avoid HTTP_CONFLICT (409) errors, run the add/delete tests in logical sequence
		if (favorites.isEmpty()) {
			// no favorites defined, we need to add then delete a known series
			seriesId = TestData.SERIES_TVDB_ID;
			addFirst = true;
		} else {
			// some favorite(s) defined, choose one to delete then add back
			seriesId = Integer.valueOf(favorites.get(0));
		}
		
		if (addFirst) {
			// Add first then...
			addFavorite(seriesId, false);
			addFavorite(seriesId, true);
		}
		
		// Delete (in every scenario)
		deleteFavorite(seriesId, false);
		deleteFavorite(seriesId, true);
		
		if (!addFirst) {
			// ... first then Add
			addFavorite(seriesId, false);
			addFavorite(seriesId, true);
		}
		
		// Now that we have modified the list twice, it should match what we started with
		List<String> updatedFavorites = getFavorites();
		assertThat(updatedFavorites).containsExactlyElementsIn(favorites);
	}
	
	protected List<UserRating> getRatings() throws IOException {
		Call<UserRatingsResponse> call = getTheTvdb().user().ratings();
		UserRatingsResponse ratingsResponse = executeCall(call);
		assertThat(ratingsResponse.data).isNotNull();
		return ratingsResponse.data;
	}
	
	protected List<UserRating> queryRating(String ratingType) throws IOException {
		// TODO	as of 2020-02-19 the v3 API for this always returns "No queries provided" (error 405)
		//		Issue raised: https://forums.thetvdb.com/viewtopic.php?f=118&t=62749
		Call<UserRatingsResponse> call = getTheTvdb().user().ratingsQuery(ratingType);
		UserRatingsResponse ratingsResponse = executeCall(call);
		assertThat(ratingsResponse.data).isNotNull();
		return ratingsResponse.data;
	}
	
	protected List<String> queryRatingParams() throws IOException {
		Call<UserRatingsQueryParamsRepsonse> call = getTheTvdb().user().ratingsQueryParams();
		UserRatingsQueryParamsRepsonse paramsResponse = executeCall(call);
		assertThat(paramsResponse).isNotNull();
		return paramsResponse.data;
	}
	
	protected void deleteRating(UserRating rating, boolean expectConflict) throws IOException {
		Call<UserRatingsResponse> call = getTheTvdb().user().deleteRating(rating.ratingType, rating.ratingItemId);
		
		if (expectConflict) {
			Response<UserRatingsResponse> errorResponse = executeExpectedErrorCall(call, HttpURLConnection.HTTP_CONFLICT);
			assertThat(errorResponse.code()).isEqualTo(HttpURLConnection.HTTP_CONFLICT);
		} else {
			UserRatingsResponse ratingsResponse = executeCall(call);
			assertThat(ratingsResponse.data).isNotNull();
		}
	}
	
	protected void addRating(UserRating rating, boolean expectConflict) throws IOException {
		Call<UserRatingsResponse> call = getTheTvdb().user().addRating(rating.ratingType, rating.ratingItemId, rating.rating);
		
		if (expectConflict) {
			// conflicts should only happen here if the rating value is outside the range [1, 10]
			Response<UserRatingsResponse> errorResponse = executeExpectedErrorCall(call, HttpURLConnection.HTTP_CONFLICT);
			assertThat(errorResponse.code()).isEqualTo(HttpURLConnection.HTTP_CONFLICT);;
		} else {
			UserRatingsResponse ratingsResponse = executeCall(call);
			assertThat(ratingsResponse.data).isNotNull();
		}
	}
	
	@Test
	public void test_ratingsCombined() throws IOException {
		doUserLogin();
		
		// Test get query params - but we have no use for them
		queryRatingParams();
		
		// Get the current user ratings
		List<UserRating> originalRatings = getRatings();
		List<UserRating> tempRatings = null;
		
		UserRating rating = null;
		boolean addFirst = false;
		
		// In order to avoid HTTP_CONFLICT (409) errors, run the add/delete tests in logical sequence
		if (originalRatings.isEmpty()) {
			// no ratings defined, we need to add then delete a rating for a known series
			rating = new UserRating();
			rating.ratingType = TestData.RATING_TYPE;
			rating.ratingItemId = TestData.SERIES_TVDB_ID;
			rating.rating = TestData.RATING_VALUE;
			addFirst = true;
		} else {
			// some rating(s) defined, choose one to delete then add back
			rating = originalRatings.get(0);
		}
		
		if (addFirst) {
			// Add first then...
			addRating(rating, false);
			tempRatings = getRatings();
			assertThat(tempRatings).contains(rating);
		}
		
//		// Query for the rating we haven't yet deleted
// TODO - test this after /user/ratings/query is fixed (see above)
//		List<UserRating> queryBefore = queryRating(rating.ratingType);
//		assertThat(queryBefore).contains(rating);
		
		// Delete (in every scenario)
		deleteRating(rating, false);
		deleteRating(rating, true);
		tempRatings = getRatings();
		assertThat(tempRatings).doesNotContain(rating);
		
//		// Query for the rating we just deleted
// TODO - test this after /user/ratings/query is fixed (see above)
//		List<UserRating> queryAfter = queryRating(rating.ratingType);
//		assertThat(queryAfter).doesNotContain(rating);
		
		if (!addFirst) {
			// ... first then Add
			addRating(rating, false);
			tempRatings = getRatings();
			assertThat(tempRatings).contains(rating);
		}
		
		// Now that we have modified the list twice, it should match what we started with
		List<UserRating> updatedRatings = getRatings();
		assertThat(updatedRatings).containsExactlyElementsIn(originalRatings);
		
		// Just for fun, make sure adding ratings outside of the [MIN, MAX] range fail appropriately
		rating.rating = UserRating.MIN_RATING.intValue() - 1; // too small
		addRating(rating, true);
		rating.rating = UserRating.MAX_RATING.intValue() + 1; // too large
		addRating(rating, true);
	}
}

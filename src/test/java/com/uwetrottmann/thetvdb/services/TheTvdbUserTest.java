package com.uwetrottmann.thetvdb.services;

import static com.google.common.truth.Truth.assertThat;

import com.uwetrottmann.thetvdb.BaseTestCase;
import com.uwetrottmann.thetvdb.TestData;
import com.uwetrottmann.thetvdb.entities.LoginData;
import com.uwetrottmann.thetvdb.entities.Token;
import com.uwetrottmann.thetvdb.entities.UserFavoritesResponse;
import com.uwetrottmann.thetvdb.entities.UserRating;
import com.uwetrottmann.thetvdb.entities.UserRatingsResponse;
import com.uwetrottmann.thetvdb.entities.UserResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

public class TheTvdbUserTest extends BaseTestCase {
    private void doUserLogin() throws IOException {
        // According to docs, user name and password should be required, but it works without.
        doUserLogin("", "", false);
    }

    private void doUserLogin(String userName, String userPassKey, boolean expectUnauthorized) throws IOException {
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
        assertThat(response.data.userName).isEqualTo("tripmckay");
    }

    private List<String> getFavorites() throws IOException {
        Call<UserFavoritesResponse> call = getTheTvdb().user().favorites();
        UserFavoritesResponse response = executeCall(call);
        assertThat(response.data).isNotNull();
        assertThat(response.data.favorites).isNotNull();
        return response.data.favorites;
    }

    private void addFavorite(boolean expectConflict) throws IOException {
        Call<UserFavoritesResponse> call = getTheTvdb().user().addFavorite(TestData.SERIES_TVDB_ID);

        if (expectConflict) {
            Response<UserFavoritesResponse> errorResponse =
                    executeExpectedErrorCall(call, HttpURLConnection.HTTP_CONFLICT);
            assertThat(errorResponse).isNotNull();
            assertThat(errorResponse.code()).isEqualTo(HttpURLConnection.HTTP_CONFLICT);
        } else {
            executeCall(call);
        }
    }

    private void deleteFavorite(boolean expectConflict) throws IOException {
        Call<UserFavoritesResponse> call = getTheTvdb().user().deleteFavorite(TestData.SERIES_TVDB_ID);

        if (expectConflict) {
            Response<UserFavoritesResponse> errorResponse =
                    executeExpectedErrorCall(call, HttpURLConnection.HTTP_CONFLICT);
            assertThat(errorResponse).isNotNull();
            assertThat(errorResponse.code()).isEqualTo(HttpURLConnection.HTTP_CONFLICT);
        } else {
            executeCall(call);
        }
    }

    @Test
    public void test_favoritesCombined() throws IOException {
        doUserLogin();

        // Get the current user favorites
        List<String> favorites = getFavorites();

        // If it exists, delete the favorite we want to add.
        for (int i = 0, favoritesSize = favorites.size(); i < favoritesSize; i++) {
            int tvdbId = Integer.parseInt(favorites.get(i));
            if (TestData.SERIES_TVDB_ID == tvdbId) {
                deleteFavorite(false);
                favorites.remove(i);
                break;
            }
        }

        // Note: favorites data returned by add/delete methods is unreliable, so check with actual get call.

        // Add favorite.
        addFavorite(false);
        addFavorite(true);
        favorites.add(String.valueOf(TestData.SERIES_TVDB_ID));

        // Verify.
        List<String> updatedFavorites = getFavorites();
        assertThat(updatedFavorites).containsExactlyElementsIn(favorites);

        // Delete again
        deleteFavorite(false);
        deleteFavorite(true);
        favorites.remove(favorites.size() - 1); // Was added last.

        // Verify.
        updatedFavorites = getFavorites();
        assertThat(updatedFavorites).containsExactlyElementsIn(favorites);
    }

    private List<UserRating> getRatings() throws IOException {
        Call<UserRatingsResponse> call = getTheTvdb().user().ratings();
        UserRatingsResponse ratingsResponse = executeCall(call);
        assertThat(ratingsResponse.data).isNotNull();
        return ratingsResponse.data;
    }

    private void deleteRating(UserRating rating, boolean expectConflict) throws IOException {
        if (rating.ratingItemId == null) {
            throw new IllegalArgumentException("ratingItemId must not be null");
        }

        Call<UserRatingsResponse> call = getTheTvdb().user().deleteRating(rating.ratingType, rating.ratingItemId);

        if (expectConflict) {
            Response<UserRatingsResponse> errorResponse =
                    executeExpectedErrorCall(call, HttpURLConnection.HTTP_CONFLICT);
            assertThat(errorResponse.code()).isEqualTo(HttpURLConnection.HTTP_CONFLICT);
        } else {
            UserRatingsResponse ratingsResponse = executeCall(call);
            assertThat(ratingsResponse.data).isNotNull();
        }
    }

    private void addRating(UserRating rating, boolean expectConflict) throws IOException {
        if (rating.ratingItemId == null) {
            throw new IllegalArgumentException("ratingItemId must not be null");
        }
        if (rating.rating == null) {
            throw new IllegalArgumentException("rating must not be null");
        }

        Call<UserRatingsResponse> call =
                getTheTvdb().user().addRating(rating.ratingType, rating.ratingItemId, rating.rating);

        if (expectConflict) {
            // conflicts should only happen here if the rating value is outside the range [1, 10]
            Response<UserRatingsResponse> errorResponse =
                    executeExpectedErrorCall(call, HttpURLConnection.HTTP_CONFLICT);
            assertThat(errorResponse.code()).isEqualTo(HttpURLConnection.HTTP_CONFLICT);
        } else {
            UserRatingsResponse ratingsResponse = executeCall(call);
            assertThat(ratingsResponse.data).isNotNull();
        }
    }

    @Test
    public void test_ratingsCombined() throws IOException {
        doUserLogin();

        // Get the current user ratings
        List<UserRating> originalRatings = getRatings();

        UserRating rating = new UserRating();
        rating.ratingType = TestData.RATING_TYPE;
        rating.ratingItemId = TestData.SERIES_TVDB_ID;
        rating.rating = TestData.RATING_VALUE;

        for (int i = 0; i < originalRatings.size(); i++) {
            UserRating origRating = originalRatings.get(i);
            if (rating.ratingType.equals(origRating.ratingType)
                    && rating.ratingItemId.equals(origRating.ratingItemId)) {
                deleteRating(origRating, false);
                originalRatings.remove(i);
                break;
            }
        }

        // Add rating.
        addRating(rating, false);
        originalRatings.add(rating);

        // Verify.
        List<UserRating> tempRatings = getRatings();
        assertThat(tempRatings).containsExactlyElementsIn(originalRatings);

        // Delete rating.
        deleteRating(rating, false);
        deleteRating(rating, true);
        originalRatings.remove(originalRatings.size() - 1); // Was added last.

        // Verify.
        tempRatings = getRatings();
        assertThat(tempRatings).containsExactlyElementsIn(originalRatings);
    }

    @Test
    public void addRating_outsideRange_conflict() throws IOException {
        doUserLogin();

        UserRating rating = new UserRating();
        rating.ratingType = TestData.RATING_TYPE;
        rating.ratingItemId = TestData.SERIES_TVDB_ID;

        rating.rating = UserRating.MIN_RATING - 1; // too small
        addRating(rating, true);

        rating.rating = UserRating.MAX_RATING + 1; // too large
        addRating(rating, true);
    }
}

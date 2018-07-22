package com.uwetrottmann.thetvdb.entities;

import java.util.List;

public class Episode {

    public Integer id;
    public Integer absoluteNumber;
    public Integer airedEpisodeNumber;
    public Integer airedSeason;
    public Integer airedSeasonID;
    public Double dvdEpisodeNumber;
    public Integer dvdSeason;
    public String episodeName;
    /** ISO 8601 date string, like "2010-09-20". */
    public String firstAired;
    /** ISO 639-1 language codes, like "en". */
    public Translations language;
    /** Time in seconds, like 1430845514. */
    public Long lastUpdated;
    public String overview;

    public Integer airsAfterSeason;
    public Integer airsBeforeEpisode;
    public Integer airsBeforeSeason;
    public List<String> directors;
    public String dvdChapter;
    public String dvdDiscid;
    /** Episode image path suffix, like "episodes/83462/398671.jpg". */
    public String filename;
    public List<String> guestStars;
    public String imdbId;
    /** TheTVDB user id. */
    public Integer lastUpdatedBy;
    public String productionCode;
    /** TheTVDB series id. */
    public Integer seriesId;
    public String showUrl;
    /** Value from 0.0 to 10.0. */
    public Double siteRating;
    public Integer siteRatingCount;
    public String thumbAdded;
    /** TheTVDB user id. */
    public Integer thumbAuthor;
    public String thumbWidth;
    public String thumbHeight;
    public List<String> writers;

    public static class Translations {
        public String episodeName;
        public String overview;
    }
}

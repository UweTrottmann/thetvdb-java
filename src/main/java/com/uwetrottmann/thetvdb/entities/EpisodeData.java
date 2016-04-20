package com.uwetrottmann.thetvdb.entities;

import java.util.List;

public class EpisodeData {

    public Episode data;

    public static class Episode extends BasicEpisode {
        public List<String> guestStars;
        public String director;
        public List<String> writers;
        public String productionCode;
        public String showUrl;
        public long lastUpdated;
        public String dvdDiscid;
        public String dvdChapter;
        public String filename;
        public int seriesId;
        public Integer lastUpdatedBy;
        public Integer airsAfterSeason;
        public Integer airsBeforeSeason;
        public Integer airsBeforeEpisode;
        public Integer thumbAuthor;
        public String thumbAdded;
        public String thumbWidth;
        public String thumbHeight;
        public String imdbId;
        public Double siteRating;
    }

}

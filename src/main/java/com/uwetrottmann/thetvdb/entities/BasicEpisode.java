package com.uwetrottmann.thetvdb.entities;

public class BasicEpisode {

    public Integer id;
    public Integer absoluteNumber;
    public Integer airedEpisodeNumber;
    public Integer airedSeason;
    public Integer airedSeasonID;
    public Integer dvdEpisodeNumber;
    public Integer dvdSeason;
    public String episodeName;
    public String firstAired;
    public Translations language;
    public String overview;

    public static class Translations {
        public String episodeName;
        public String overview;
    }

}

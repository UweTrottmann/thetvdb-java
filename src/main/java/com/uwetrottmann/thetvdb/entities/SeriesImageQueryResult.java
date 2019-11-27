package com.uwetrottmann.thetvdb.entities;

public class SeriesImageQueryResult {

    public int id;
    public String keyType;
    public String subKey;
    public String fileName;
    public String resolution ;
    public RatingsInfo ratingsInfo;
    public String thumbnail;
    /** Appears unused, always 0. */
    public Integer languageId;
    public String language;


    public class RatingsInfo {
        public Double average;
        public Integer count;
    }

}

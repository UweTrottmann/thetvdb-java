package com.uwetrottmann.thetvdb.entities;

public class SeriesImageQueryResult {

    public int id;
    public String keyType;
    public String subKey;
    public String fileName;
    public String resolution;
    public RatingsInfo ratingsInfo;
    public String thumbnail;
    /** Appears unused, always 0. */
    public Integer languageId;
    public String language;


    public class RatingsInfo {
        /**
         * @deprecated Use {@link #count} instead. The value returned for average is now equal to the favorite count. So
         * it also may be larger than the previous maximum value 10.
         */
        @Deprecated
        public Double average;
        public Integer count;
    }

}

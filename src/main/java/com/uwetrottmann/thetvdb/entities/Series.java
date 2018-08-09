package com.uwetrottmann.thetvdb.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Series record.
 *
 * @see <a href="http://www.thetvdb.com/wiki/index.php?title=API:Base_Series_Record">API:Base Series Record</a>
 */
public class Series {

    /**
     * An unsigned integer assigned by our site to the series. It does not change and will always represent the same
     * series. Cannot be null.
     */
    public Integer id;
    public String seriesName;
    public List<String> aliases = new ArrayList<>();
    public String slug;

    /** Image path suffix, like "graphical/83462-g20.jpg". */
    public String banner;
    /**
     * A string containing either "Ended" or "Continuing". Can be null.
     */
    public String status;
    /** ISO 8601 date string, like "2010-09-20". */
    public String firstAired;
    public String network;
    public String networkId;
    /** In minutes. */
    public String runtime;
    public List<String> genre = new ArrayList<>();
    public String overview;
    /** Time in seconds, like 1430845514. */
    public Long lastUpdated;
    /** An English day string, like "Monday". */
    public String airsDayOfWeek;
    /** In most cases a AM/PM time string, like "9:00 PM". Good luck with this. */
    public String airsTime;
    /** US rating, like "TV-MA". */
    public String rating;
    public String imdbId;
    public String zap2itId;
    /** ISO 8601 date-time string, like "2010-09-20 15:05:50". */
    public String added;
    /** TheTVDB user id. */
    public Integer addedBy;
    /** Value from 0.0 to 10.0. */
    public Double siteRating;
    public Integer siteRatingCount;

}

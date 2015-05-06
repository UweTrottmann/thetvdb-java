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

    /**
     * A string which should be appended to &lt;mirrorpath&gt;/banners/ to determine the actual location of the artwork.
     * Returns the highest voted poster for the requested series. Can be null.
     */
    public String poster;

    /**
     * An unsigned integer representing the series ID at tv.com.
     *
     * @deprecated As TV.com now only uses these ID's internally it's of little use and no longer updated. Can be null.
     */
    @Deprecated
    public Integer seriesId;

    /**
     * A string containing either "Ended" or "Continuing". Can be null.
     */
    public String status;
    public String firstAired;
    public String network;
    public String networkId;
    public String runtime;
    public List<String> genre = new ArrayList<>();
    public List<String> actors = new ArrayList<>();
    public String overview;
    public Integer lastUpdated;
    public String airsDayOfWeek;
    public String airsTime;
    public String rating;
    public String imdbId;
    public String zap2itId;
    public String added;

}

package com.uwetrottmann.thetvdb.entities;

public class Actor {

    public Integer id;
    public Integer seriesId;
    public String name;
    public String role;
    public Integer sortOrder;
    public String image;
    public Integer imageAuthor;
    /** ISO 8601 date-time string, like "2010-09-20 15:05:50". */
    public String imageAdded;
    /** ISO 8601 date-time string, like "2010-09-20 15:05:50". */
    public String lastUpdated;
}

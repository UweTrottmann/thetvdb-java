package com.uwetrottmann.thetvdb.entities;

import java.util.List;

public class SeriesImageQueryResults {

    public List<SeriesImageQueryResult> data;

    public class SeriesImageQueryResult {

        public int id;
        public String keyType;
        public String subKey;
        public String fileName;
        public String resolution ;
        public RatingsInfo ratingsInfo;
        public String thumbnail;

        public class RatingsInfo {
            public Double average;
        }

    }
}

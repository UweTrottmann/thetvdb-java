package com.uwetrottmann.thetvdb.entities;

public class UserRating {
    public static final Integer MIN_RATING	= Integer.valueOf(1);
    public static final Integer MAX_RATING	= Integer.valueOf(10);

    public static final String TYPE_BANNER	= "banner";
    public static final String TYPE_EPISODE	= "episode";
    public static final String TYPE_SERIES	= "series";

    public Integer ratingItemId;
    public Integer rating;
    public String ratingType;

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
            obj instanceof UserRating &&
                equals(((UserRating) obj).ratingItemId, ratingItemId) &&
                equals(((UserRating) obj).rating, rating) &&
                equals(((UserRating) obj).ratingType, ratingType);
    }

    protected boolean equals(Object o1, Object o2) {
        return (o1 == o2) ||
            (o1 != null && o1.equals(o2)) ||
            (o2 != null && o2.equals(o1));
        }
}

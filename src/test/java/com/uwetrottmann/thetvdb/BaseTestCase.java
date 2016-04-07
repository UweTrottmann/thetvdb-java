package com.uwetrottmann.thetvdb;

import org.junit.BeforeClass;

public abstract class BaseTestCase {

    protected static final String API_KEY = "";

    private static final boolean DEBUG = true;

    private static final TheTvdb theTvdb = new TheTvdb(API_KEY);

    @BeforeClass
    public static void setUpOnce() {
        if (API_KEY.length() == 0) {
            throw new IllegalArgumentException("Set a valid value for API_KEY.");
        }
        theTvdb.enableDebugLogging(DEBUG);
    }

    protected final TheTvdb getTheTvdb() {
        return theTvdb;
    }

}

package com.uwetrottmann.thetvdb;

import org.junit.BeforeClass;

public abstract class BaseTestCase {

    /** <b>WARNING:</b> do not use this in your code. This is for testing purposes only. */
    protected static final String API_KEY = "0B0BB36928753CD8";

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

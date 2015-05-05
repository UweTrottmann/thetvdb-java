package com.uwetrottmann.thetvdb;

import org.junit.BeforeClass;

public abstract class BaseTestCase {

    // Do NOT use this token in your application, for testing purposes only!
    private static final String JSON_WEB_TOKEN = "";

    private static final boolean DEBUG = true;

    private static final TheTvdb theTvdb = new TheTvdb();

    @BeforeClass
    public static void setUpOnce() {
        theTvdb.setJsonWebToken(JSON_WEB_TOKEN);
        theTvdb.setIsDebug(DEBUG);
    }

    protected final TheTvdb getTheTvdb() {
        return theTvdb;
    }

}

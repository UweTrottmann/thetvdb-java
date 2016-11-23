package com.uwetrottmann.thetvdb.entities;

import java.util.List;

public class ErrorResponse {

    public JsonErrors errors;

    public static class JsonErrors {
        public List<String> invalidFilters;
        /** Non-null if some translations were not available in the specified language. */
        public String invalidLanguage;
        public List<String> invalidQueryParams;
    }

}

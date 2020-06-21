package com.uwetrottmann.thetvdb.entities;

public class LoginData {

    public String apikey;
    public String username;
    public String userkey;

    public LoginData(String apikey) {
        this.apikey = apikey;
    }

    public LoginData user(String username, String userkey) {
        this.username = username;
        this.userkey = userkey;
        return this;
    }

}

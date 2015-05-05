package com.uwetrottmann.thetvdb.entities;

public class LoginData {

    public String apikey;
    public String username;
    public String userpass;

    public LoginData(String apikey) {
        this.apikey = apikey;
    }

    public LoginData user(String username, String userpass) {
        this.username = username;
        this.userpass = userpass;
        return this;
    }

}

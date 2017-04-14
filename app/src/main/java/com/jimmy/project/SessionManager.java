package com.jimmy.project;

/**
 * Created by jimmy on 4/13/17.
 */

public class SessionManager {
    private static SessionManager _instance = new SessionManager();

    private String token;

    private SessionManager() {};

    public static SessionManager getInstance() {
        return _instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package com.tiy.web;

/**
 * Created by Paul Dennis on 1/6/2017.
 */
public class UserRequest {
    private String title;
    private String userName;

    public UserRequest () {

    }

    public UserRequest (String title, String userName) {
        this.title = title;
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public String getUserName() {
        return userName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

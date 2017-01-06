package com.tiy.web;

/**
 * Created by Paul Dennis on 1/5/2017.
 */
public class User {

    private String userName;
    private int id;
    private String firstName;
    private String lastName;
    private String password;

    public User(int id, String userName, String firstName, String lastName, String password) {
        this.userName = userName;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

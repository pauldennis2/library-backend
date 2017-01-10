package com.tiy.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Paul Dennis on 1/5/2017.
 */
public class Book {

    int id;
    String title;
    String author;
    String genre;
    String checkedOutBy;
    boolean checkedOut;

    public Book() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;

        checkedOutBy = "none";
        checkedOut = false;
    }

    public Book(String title, String author, String genre, String checkedOutBy) {
        if (checkedOutBy == null) {
            throw new AssertionError("user cannot be null");
        }
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.checkedOutBy = checkedOutBy;
        if (checkedOutBy.equals("none")) {
            checkedOut = false;
        } else {
            checkedOut = true;
        }
    }

    public Book(int id, String title, String author, String genre, String checkedOutBy) {
        if (checkedOutBy == null) {
            throw new AssertionError("user cannot be null");
        }
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.checkedOutBy = checkedOutBy;
        if (checkedOutBy.equals("none")) {
            checkedOut = false;
        } else {
            checkedOut = true;
        }
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getId() {
        return id;
    }

    public String getCheckedOutBy() {
        return checkedOutBy;
    }

    public void setCheckedOutBy(String user) {
        if (user == null) {
            throw new AssertionError("user cannot be null (in book.setCheckedOutBy()");
        }
        checkedOutBy = user;
        if (user.equals("none")) {
            checkedOut = false;
        } else {
            checkedOut = true;
        }
    }
}
package com.tiy.web;

import java.util.Date;

/**
 * Created by Paul Dennis on 1/5/2017.
 */
public class Book {

    int id;
    boolean checkedOut;
    String dueDate;
    String title;
    String author;
    String genre;
    //User checkedOutBy;
    int checkedOutById

    public Book (String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;

        dueDate = null;
        checkedOut = false;
        checkedOutBy = null;
    }

    public Book(int id, String title, String author, String genre, int checkedOutById, String dueDate) {
        this.id = id;
        this.checkedOut = checkedOut;
        this.dueDate = dueDate;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.checkedOutById = checkedOutById;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public Date getDueDate() {
        return dueDate;
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

    public User getCheckedOutBy() {
        return checkedOutBy;
    }
}

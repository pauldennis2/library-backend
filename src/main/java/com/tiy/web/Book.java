package com.tiy.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    //int checkedOutById;
    String checkedOutBy;

    public Book (String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;

        dueDate = null;
        checkedOut = false;
        //checkedOutById = -1;
        checkedOutBy = null;
    }

    public Book (String title, String author, String genre, String checkedOutBy) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.checkedOutBy = checkedOutBy;
        if (checkedOutBy != null) {
             checkedOut = true;
        } else {
            checkedOut = false;
        }
    }

    public Book(int id, String title, String author, String genre, String checkedOutBy/*, String dueDate*/) {
        this.id = id;
        this.dueDate = dueDate;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.checkedOutBy = checkedOutBy;
        if (checkedOutBy != null) {
            checkedOut = true;
        } else {
            checkedOut = false;
        }
        //this.checkedOutById = checkedOutById;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public String getDueDate() {
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

    /*public int getCheckedOutById () {
        return checkedOutById;
    }*/

    public int getId() {
        return id;
    }

    public String getCheckedOutBy () {
        if (checkedOutBy == null) {
            return "none";
        }
        return checkedOutBy;
    }

    public void setCheckedOutBy (String user) {
        checkedOutBy = user;
        if (user == null) {
            checkedOut = false;
        } else {
            checkedOut = true;
        }
    }

    public static String getDateFromLocalDateTime () {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY MMM DD");
        return time.format(formatter);
    }

    public static LocalDateTime getLocalDateTimeFromString (String formattedDateTime) {
        return null;
    }
}

package com.tiy.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.*;

/**
 * Created by Paul Dennis on 1/6/2017.
 */
public class BookDatabaseTest {

    BookDatabase bookDatabase;
    Connection conn;

    public static final String DB_PATH = "jdbc:h2:./main";

    @Before
    public void setUp() throws Exception {
        bookDatabase = new BookDatabase();
        conn = DriverManager.getConnection(DB_PATH);
    }

    @After
    public void tearDown() throws Exception {

    }



}
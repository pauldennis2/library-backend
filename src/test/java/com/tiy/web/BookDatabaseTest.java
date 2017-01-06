package com.tiy.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

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
        if (bookDatabase == null) {
            bookDatabase = new BookDatabase();
            conn = DriverManager.getConnection(DB_PATH);
            bookDatabase.init();
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitDb () throws SQLException {

    }

    @Test
    public void testInsertUser () throws SQLException {
        String userName = "test";
        String blah = "Blah";
        int newUserId = bookDatabase.insertUser(conn, userName, blah, blah, blah);
        //^^ Because that's how multiple strings makes me feel
        PreparedStatement statement = conn.prepareStatement("Select * from users where username = ?");
        statement.setString(1, userName);
        ResultSet results = statement.executeQuery();
        assertNotNull(results);
        int numResults = 0;
        int retrievedUserId = -1;
        while (results.next()) {
            numResults++;
            retrievedUserId = results.getInt("id");
        }
        assertEquals(1, numResults);
        assertEquals(newUserId, retrievedUserId);

        bookDatabase.deleteUser(conn, userName);

        results = statement.executeQuery();

        numResults = 0;
        while (results.next()) {
            numResults++;
        }
        assertEquals(0, numResults);
    }

    @Test
    public void testInsertBook () throws SQLException {
        Book book = new Book ("The Big Book of Unit Tests", "Dom", "Coding Books");
        bookDatabase.insertBook(conn, book);

        PreparedStatement statement = conn.prepareStatement("Select * from books where title = ?");
        statement.setString(1, book.getTitle());
        ResultSet results = statement.executeQuery();
        assertNotNull(results);
        results.next();
        assertEquals("Dom", results.getString("author"));
        assertEquals("Coding Books", results.getString("genre"));

        bookDatabase.deleteBook(conn, book.getTitle());

        results = statement.executeQuery();

        int numResults = 0;
        while (results.next()) {
            numResults++;
        }
        assertEquals(0, numResults);
    }



}
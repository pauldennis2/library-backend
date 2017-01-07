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

    static BookDatabase bookDatabase;
    Connection conn;
    public static final String DB_PATH = "jdbc:h2:./main";

    @Before
    public void setUp() throws Exception {
        conn = DriverManager.getConnection(DB_PATH);
        if (bookDatabase == null) {
            bookDatabase = new BookDatabase();
            bookDatabase.init();
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitDb () throws SQLException {
        PreparedStatement todoQuery = conn.prepareStatement("SELECT * FROM books");
        ResultSet results = todoQuery.executeQuery();
        assertNotNull(results);
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

    @Test
    public void testDeleteById () throws SQLException {
        String bookName = "Boring Test Book";
        Book king1 = new Book (bookName, "Smith", "Fantasy");
        Book king2 = new Book (bookName, "Smith", "Fantasy");

        int id1 = bookDatabase.insertBook(conn, king1);
        int id2 = bookDatabase.insertBook(conn, king2);

        bookDatabase.deleteBookById(conn, id1);


        Book retrievedBook = bookDatabase.retrieveBook(conn, bookName);
        assertEquals(id2, retrievedBook.getId());
        bookDatabase.deleteBook(conn, bookName);
    }
}
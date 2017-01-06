package com.tiy.web;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Dennis on 1/5/2017.
 */
public class BookDatabase {

    private Server server;

    public final static String DB_PATH = "jdbc:h2:./main";

    public void init () throws SQLException {
        server = Server.createWebServer();
        server.start();
        Connection conn = DriverManager.getConnection(DB_PATH);
        Statement statement = conn.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS books " +
                "(id IDENTITY, title VARCHAR, author VARCHAR, genre VARCHAR, checked_out_user_id int, due_date VARCHAR");
        statement.execute("CREATE TABLE IF NOT EXISTS users " +
                "(id IDENTITY, username VARCHAR, first_name VARCHAR, last_name VARCHAR, password VARCHAR)");
    }

    public void insertBook(Connection conn, Book book) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO todos VALUES (NULL, ?, ?, ?, ?)");
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getGenre());
        if (book.getCheckedOutBy() == null) {
            statement.setInt(4, -1);
        } else {
            statement.setInt(4, book.getCheckedOutBy().getId());
        }
        statement.execute();
    }

    public int insertUser(Connection conn, String username, String firstName, String lastName, String password) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, firstName);
        statement.setString(3, lastName);
        statement.setString(4, password);
        statement.execute();

        statement = conn.prepareStatement("SELECT id FROM users where username = ?");
        statement.setString(1, username);
        ResultSet results = statement.executeQuery();
        results.next();
        return results.getInt("id");
    }

    public void deleteUser(Connection conn, String username) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM users where username = ?");
        statement.setString(1, username);
        statement.execute();
    }

    public void deleteBook(Connection conn, String text, int userId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM todos where text = ? AND user_id = ?");
        statement.setString(1, text);
        statement.setInt(2, userId);
        statement.execute();
    }

    public List<Book> selectAllBooks(Connection conn) throws SQLException {
        List<Book> books = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM books");
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            int id = results.getInt("id");
            String title = results.getString("text");
            String author = results.getString("author");
            String genre = results.getString("genre");
            int user_id = results.getInt("user_checked_out_id");
            String dueDate = results.getString("due_date");
            books.add(new Book(id, title, author, genre, user_id, dueDate));
        }
        return books;
    }

    public Book retrieveBook (Connection conn, String title) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select * from books where title = ?");
        statement.setString(1, title);
        ResultSet results = statement.executeQuery();
        results.next();
        int id = results.getInt("id");
        String retrievedTitle = results.getString("text");
        String author = results.getString("author");
        String genre = results.getString("genre");
        int user_id = results.getInt("user_checked_out_id");
        String dueDate = results.getString("due_date");
        Book retrievedBook = new Book (id, retrievedTitle, author, genre, user_id, dueDate);
        return retrievedBook;
    }

    public User retrieveUser (Connection conn, String userName) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select * from users where username = ?");
        statement.setString(1, userName);
        ResultSet results = statement.executeQuery();
        results.next();
        int id = results.getInt("id");
        String retrievedUserName = results.getString("username");
        String firstName = results.getString("first_name");
        String lastName = results.getString("last_name");
        String password = results.getString("password");
        return new User(id, retrievedUserName, firstName, lastName, password);
    }

    public void closeServer () {
        server.stop();
    }
}

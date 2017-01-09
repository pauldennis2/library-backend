package com.tiy.web;

import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Dennis on 1/5/2017.
 */
public class BookDatabase {

    private Server server;

    public final static String DB_PATH = "jdbc:h2:./main";

    //Agreement: "none" is a reserved username

    public BookDatabase () {
    }

    public void init () throws SQLException {
        server = Server.createWebServer();
        server.start();
        Connection conn = DriverManager.getConnection(DB_PATH);
        Statement statement = conn.createStatement();
        statement.execute("Create table if not exists books " +
                        "(id identity, title varchar, author varchar, genre varchar, user varchar)");
        statement.execute("CREATE TABLE IF NOT EXISTS users " +
                "(id IDENTITY, username VARCHAR, first_name VARCHAR, last_name VARCHAR, password VARCHAR)");
    }

    public int insertBook(Connection conn, Book book) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO books VALUES (NULL, ?, ?, ?, ?)");
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getGenre());
        statement.setString(4, book.getCheckedOutBy());
        /*if (book.getCheckedOutBy() == null) {
            statement.setInt(4, -1);
        } else {
            statement.setInt(4, book.getCheckedOutBy().getId());
        }*/
//        statement.setInt(4, book.getCheckedOutById());
        //statement.setString(5, book.getDueDate());
        statement.execute();
        //This will always return the most recently added book (the one we added above)
        PreparedStatement titleQueryStatement = conn.prepareStatement("select id from books where title = ? order by id desc");
        titleQueryStatement.setString(1, book.getTitle());
        ResultSet results = titleQueryStatement.executeQuery();
        results.next();
        return results.getInt("id");
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

    public void deleteBook(Connection conn, String title) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM books where title = ?");
        statement.setString(1, title);
        statement.execute();
    }

    public void deleteBookById (Connection conn, int id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Delete from books where id = ?");
        statement.setInt(1, id);
        statement.execute();
    }

    public List<Book> selectAllBooks(Connection conn) throws SQLException {
        List<Book> books = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM books");
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            int id = results.getInt("id");
            String title = results.getString("title");
            String author = results.getString("author");
            String genre = results.getString("genre");
            String user = results.getString("user");
            books.add(new Book(id, title, author, genre, user));
        }
        return books;
    }

    public List<Book> selectAllBooksWithCheckedOutStatus (Connection conn, boolean checkedOut) throws SQLException {
        List<Book> books = new ArrayList<>();
        PreparedStatement statement;
        if (checkedOut) {
            statement = conn.prepareStatement("Select * from books where user != ?");
        } else {
            statement = conn.prepareStatement("Select * from books where user = ?");
        }
        statement.setString(1, "none");
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            int id = results.getInt("id");
            String title = results.getString("text");
            String author = results.getString("author");
            String genre = results.getString("genre");
            String user = results.getString("user");
            books.add(new Book(id, title, author, genre, user));
        }
        return books;
    }

    public Book retrieveBook (Connection conn, String title) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select * from books where title = ?");
        statement.setString(1, title);
        ResultSet results = statement.executeQuery();
        results.next();
        int id = results.getInt("id");
        String retrievedTitle = results.getString("title");
        String author = results.getString("author");
        String genre = results.getString("genre");
        //int user_id = results.getInt("user_checked_out_id");
        //String dueDate = results.getString("due_date");
        String checkedOutBy = results.getString("user");
        Book retrievedBook = new Book (id, retrievedTitle, author, genre, checkedOutBy);
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

    /**
     *
     * @param conn
     * @param title
     * @param userName
     * @return true if successful, otherwise throws an assertion error
     * @throws SQLException
     */
    public boolean checkOutBook (Connection conn, String title, String userName) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select * from books where title = ? AND user = ?");
        statement.setString(1, title);
        statement.setString(2, "none");
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            int id = results.getInt("id");
            PreparedStatement checkoutStatement = conn.prepareStatement("Update books set user = ? where id = ?");
            checkoutStatement.setString(1, userName);
            checkoutStatement.setInt(2, id);
            checkoutStatement.execute();
            return true;
        }
        throw new AssertionError("Book *" + title + "* not found available for checkout");
    }

    public boolean returnBook (Connection conn, String title) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("Select * from books where title = ? and user != ?");
        statement.setString(1, title);
        statement.setString(2, "none");
        ResultSet results = statement.executeQuery();
        if (results.next()) {
            int id = results.getInt("id");
            PreparedStatement checkInStatement = conn.prepareStatement("Update books set user = ? where id = ?");
            checkInStatement.setString(1, "none");
            checkInStatement.setInt(2, id);
            checkInStatement.execute();
            return true;
        }
        throw new AssertionError("Book *" + title + "* not found to return");
    }
}

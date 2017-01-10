package com.tiy.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Dennis on 1/6/2017.
 */
@RestController
public class LibraryJSONController {

    boolean booksInit = false;
    public static final String DB_PATH = "jdbc:h2:./main";
    public static Connection conn;
    public static BookDatabase bookDatabase;

    static {
        try {
            conn = DriverManager.getConnection(DB_PATH);
            bookDatabase = new BookDatabase();
            bookDatabase.init();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(path = "/sample.json", method = RequestMethod.GET)
    public List<Book> getSampleBooks () throws SQLException {
        System.out.println("Known 'error'; this method adds duplicates each time the program is run (if called)");
        if (!booksInit) {
            bookDatabase.insertBook(conn, new Book("The Fellowship of the Ring", "J R Tolkien", "Fantasy", "none"));
            bookDatabase.insertBook(conn, new Book("The Cinder Spires", "Jim Butcher", "Steampunk/Fantasy", "none"));
            bookDatabase.insertBook(conn, new Book("The Martian", "Andy Weir", "Science Fiction", "Paul"));
            bookDatabase.insertBook(conn, new Book("Cryoburn", "Lois McMaster Bujold", "Science Fiction", "none"));
            bookDatabase.insertBook(conn, new Book("Mossflower", "Brian Jacques", "Fantasy", "none"));
            booksInit = true;
        }
        return bookDatabase.selectAllBooks(conn);
    }

    @RequestMapping(path = "/book-return.json", method = RequestMethod.POST)
    public List<Book> returnBook (@RequestBody UserRequest userRequest) throws SQLException {
        //We've agreed these should be good inputs
        String title = userRequest.getTitle();
        bookDatabase.returnBook(conn, title);
        return bookDatabase.selectAllBooks(conn);
    }

    @RequestMapping(path = "/book-checkout.json", method = RequestMethod.POST)
    public List<Book> checkoutBook (@RequestBody UserRequest userRequest) throws SQLException {
        if (!booksInit) {
            getSampleBooks();
        }
        String title = userRequest.getTitle();
        String userName = userRequest.getUserName();
        bookDatabase.checkOutBook(conn, title, userName);
        return bookDatabase.selectAllBooks(conn);
    }

    @RequestMapping(path = "/book-list.json", method = RequestMethod.GET)
    public List<Book> getAllBooks () throws SQLException {
        return bookDatabase.selectAllBooks(conn);
    }


}

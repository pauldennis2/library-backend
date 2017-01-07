package com.tiy.web;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Dennis on 1/6/2017.
 */
@RestController
public class LibraryJSONController {

    List<Book> books;
    boolean booksInit = false;

    @RequestMapping(path = "/sample.json", method = RequestMethod.GET)
    public List<Book> getSampleBooks () throws SQLException {
        if (!booksInit) {
            books = new ArrayList<Book>();
            books.add(new Book("The Fellowship of the Ring", "J R Tolkien", "Fantasy", null));
            books.add(new Book("The Cinder Spires", "Jim Butcher", "Steampunk/Fantasy", null));
            books.add(new Book("The Martian", "Andy Weir", "Science Fiction", "Paul"));
            books.add(new Book("Cryoburn", "Lois McMaster Bujold", "Science Fiction", null));
            books.add(new Book("Mossflower", "Brian Jacques", "Fantasy", null));
            booksInit = true;
        }
        System.out.println("printing books");
        for (Book book : books) {
            System.out.println(book.getTitle());
        }
        return books;
    }

    @RequestMapping(path = "/toggle-checkout.json", method = RequestMethod.POST)
    public List<Book> toggleBookCheckOut (@RequestBody UserRequest userRequest) throws SQLException {
        if (!booksInit) {
            getSampleBooks();
        }
        for (Book book : books) {
            if (book.getTitle().equals(userRequest.getTitle())) {
                book.setCheckedOutBy(userRequest.getUserName());
            }
        }
        return books;
    }

    @RequestMapping(path = "/book-return.json", method = RequestMethod.POST)
    public List<Book> returnBook (@RequestBody UserRequest userRequest) throws SQLException {
        if (!booksInit) {
            getSampleBooks();
        }
        //We've agreed these should be good inputs
        String title = userRequest.getTitle();
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                book.setCheckedOutBy(null);
                return books;
            }
        }
        throw new AssertionError("Book *" + title + "* not found to return");
    }

    @RequestMapping(path = "/book-checkout.json", method = RequestMethod.POST)
    public List<Book> checkoutBook (@RequestBody UserRequest userRequest) throws SQLException {
        if (!booksInit) {
            getSampleBooks();
        }
        //We've agreed these should be valid inputs, so the AssertionError should not be reached
        String title = userRequest.getTitle();
        String userName = userRequest.getUserName();
        for (Book book : books) {
            if (book.getTitle().equals(title) && book.getCheckedOutBy() == null) {
                book.setCheckedOutBy(userName);
                return books;
            }
        }
        throw new AssertionError("Book *" + title + "* not found to checkout");
    }

    @RequestMapping(path = "/book-list.json", method = RequestMethod.GET)
    public List<Book> getAllBooks () throws SQLException {
        return books;
    }


}

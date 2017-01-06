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
    public List<Book> getAllBooks () throws SQLException {
        books = new ArrayList<Book>();
        if (!booksInit) {
            books.add(new Book("The Fellowship of the Ring", "J R Tolkien", "Fantasy", null));
            books.add(new Book("The Cinder Spires", "Jim Butcher", "Steampunk/Fantasy", null));
            books.add(new Book("The Martian", "Andy Weir", "Science Fiction", "Paul"));
        }
        return books;
    }

    @RequestMapping(path = "/checkout", method = RequestMethod.POST)
    public List<Book> toggleBookCheckOut (@RequestBody UserRequest userRequest) throws SQLException {
        //PreparedStatement statement = conn.prepareStatement();
        if (!booksInit) {
            getAllBooks();
        }
        for (Book book : books) {
            if (book.getTitle().equals(userRequest.getTitle())) {
                book.setCheckedOutBy(userRequest.getUserName());
            }
        }
        return books;
    }


}

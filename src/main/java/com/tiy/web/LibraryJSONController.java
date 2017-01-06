package com.tiy.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Dennis on 1/6/2017.
 */
@RestController
public class LibraryJSONController {

    @RequestMapping(path = "/sample", method = RequestMethod.GET)
    public List<Book> getAllBooks () throws SQLException {
        List<Book> books = new ArrayList<Book>();
        books.add(new Book("The Fellowship of the Ring", "J R Tolkien", "Fantasy"));
        books.add(new Book("The Cinder Spires", "Jim Butcher", "Steampunk/Fantasy"));
        books.add(new Book("The Martian", "Andy Weir", "Science Fiction"));
        return books;
    }

//    @RequestMapping(path = "/checkout", method = RequestMethod.POST)
//    public
}

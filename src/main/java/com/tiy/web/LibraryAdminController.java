package com.tiy.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.tiy.web.LibraryJSONController.bookDatabase;
import static com.tiy.web.LibraryJSONController.conn;

/**
 * Created by Paul Dennis on 1/9/2017.
 */
@Controller
public class LibraryAdminController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home (Model model) throws SQLException {
        model.addAttribute("book-list", bookDatabase.selectAllBooks(conn));
        return "home";
    }

    @RequestMapping(path = "/addBook", method = RequestMethod.POST)
    public String addBook (Book book) throws SQLException {
        bookDatabase.insertBook(conn, book);
        return "redirect:/admin";
    }

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String adminMain() {
        return "admin";
    }

    @RequestMapping(path = "/deleteBook", method = RequestMethod.POST)
    public String deleteBook (String title) throws SQLException {
        bookDatabase.deleteBook(conn, title);
        return "redirect:/admin";
    }
}

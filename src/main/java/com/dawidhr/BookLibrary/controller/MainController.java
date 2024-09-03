package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.AuthorDAO;
import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.dao.BookReservedDAO;
import com.dawidhr.BookLibrary.dao.PersonDAO;
import com.dawidhr.BookLibrary.model.dashboard.AuthorDashboard;
import com.dawidhr.BookLibrary.model.dashboard.BookDashboard;
import com.dawidhr.BookLibrary.model.dashboard.BookReservedDashboard;
import com.dawidhr.BookLibrary.model.dashboard.PersonDashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Autowired
    AuthorDAO authorDAO;
    @Autowired
    BookDAO bookDAO;
    @Autowired
    PersonDAO personDAO;
    @Autowired
    BookReservedDAO bookReservedDAO;


    @RequestMapping({"/", "index"})
    public String main(Model model) {
        model.addAttribute("authorStatistic", prepareAuthorDashboard());
        model.addAttribute("bookStatistic", prepareBookStatistic());
        model.addAttribute("personStatistic", preparePersonDashboard());
        model.addAttribute("bookReservedStatistic", prepareBookReservedDashboard());
        return "main/index.html";
    }

    @RequestMapping("/login")
    public String loginAction(Model model) {
        return "main/login.html";
    }

    private BookDashboard prepareBookStatistic() {
        long booksCount = bookDAO.countAllAvailableBooks();
        long booksAddedLast30DaysCount = bookDAO.countAddedBooksInLast30Days();
        return new BookDashboard(booksCount, booksAddedLast30DaysCount);
    }


    private AuthorDashboard prepareAuthorDashboard() {
        long authorsCount = authorDAO.count();
        long authorsAddedLast30DaysCount = authorDAO.countAddedAuthorInLast30Days();
        return new AuthorDashboard(authorsCount, authorsAddedLast30DaysCount);
    }

    private PersonDashboard preparePersonDashboard() {
        long personCount = personDAO.count();
        long personRegisteredInLast30DaysCount = personDAO.countPersonRegisteredInLast30Days();
        return new PersonDashboard(personCount, personRegisteredInLast30DaysCount);
    }

    private BookReservedDashboard prepareBookReservedDashboard() {
        long bookReservedCount = bookReservedDAO.count();
        long bookReservedInLast30DaysCount = bookReservedDAO.countBookReservedInLast30Days();
        return new BookReservedDashboard(bookReservedCount, bookReservedInLast30DaysCount);
    }
}

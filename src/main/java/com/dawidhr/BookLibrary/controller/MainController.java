package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.AuthorDAO;
import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.model.dashboard.AuthorDashboard;
import com.dawidhr.BookLibrary.model.dashboard.BookDashboard;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    AuthorDAO authorDAO;
    BookDAO bookDAO;

    public MainController(AuthorDAO authorDAO, BookDAO bookDAO) {
        this.authorDAO = authorDAO;
        this.bookDAO = bookDAO;
    }

    @RequestMapping({"/", "index"})
    public String main(Model model) {
        model.addAttribute("authorStatistic", prepareAuthorDashboard());
        model.addAttribute("bookStatistic", prepareBookStatistic());
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
}

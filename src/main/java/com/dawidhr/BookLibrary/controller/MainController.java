package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.model.dashboard.AuthorDashboard;
import com.dawidhr.BookLibrary.model.dashboard.BookDashboard;
import com.dawidhr.BookLibrary.model.dashboard.BookReservedDashboard;
import com.dawidhr.BookLibrary.model.dashboard.PersonDashboard;
import com.dawidhr.BookLibrary.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @Autowired
    StatisticService statisticService;



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
       return statisticService.getBookDashboard();
    }


    private AuthorDashboard prepareAuthorDashboard() {
        return statisticService.getAuthorDashboard();
    }

    private PersonDashboard preparePersonDashboard() {
        return statisticService.getPersonDashboard();
    }

    private BookReservedDashboard prepareBookReservedDashboard() {
       return statisticService.getBookReservedDashboard();
    }
}

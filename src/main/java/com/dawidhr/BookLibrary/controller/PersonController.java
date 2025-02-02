package com.dawidhr.BookLibrary.controller;


import com.dawidhr.BookLibrary.cache.StatisticCache;
import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.dao.BookReservedDAO;
import com.dawidhr.BookLibrary.dao.PersonDAO;
import com.dawidhr.BookLibrary.helper.ProductListPage;
import com.dawidhr.BookLibrary.model.*;
import com.dawidhr.BookLibrary.model.dashboard.StatisticType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class PersonController {

    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private BookReservedDAO bookReservedDAO;
    @Autowired
    private StatisticCache statisticCache;

    @GetMapping("/persons")
    public String getAllPersons(Model model,
                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                @RequestParam(required = false, defaultValue = "5") Integer listSize,
                                @RequestParam(required = false) String search) {
        int productSizeList = ProductListPage.DEFAULT_PER_PAGE;
        List<Long> pagination = new ArrayList<>();
        if (ProductListPage.isPageSizeAvailable(listSize)) {
            productSizeList = listSize;
        }
        if (StringUtils.hasText(search)) {
            model.addAttribute("persons", personDAO.findPerson(search));
        } else {
            int bookSize = personDAO.count();
            pagination = ProductListPage.preparePagination(bookSize, productSizeList);
            model.addAttribute("persons", personDAO.getAll(PageRequest.of(page, productSizeList)));
        }
        model.addAttribute("pagination", pagination);
        model.addAttribute("listSize", listSize);
        model.addAttribute("listSizeAvailable", ProductListPage.listSizesAvailable);
        return "person/list.html";
    }

    @GetMapping("/person/add")
    public String addPersonForm(Model model) {
        model.addAttribute("person", new Person());
        return "person/add.html";
    }

    @PostMapping("/person/processAddingPerson")
    public String processAddingPerson(@Valid @ModelAttribute Person person, BindingResult bindingResult) {
        Person personFromDb = personDAO.find(person);
        if (personFromDb != null) {
            return "redirect:/error.html";
        }
        // TODO Add more validations
        if (bindingResult.hasErrors()) {
            return "person/add.html";
        }
        personDAO.insert(person);
        statisticCache.removeObject(StatisticType.PERSON_DASHBOARD);
        return "redirect:/persons";
    }

    @GetMapping("/person/{id}/view")
    public String show(@PathVariable("id") long id, Model model) {
        Person person = personDAO.getById(id);
        if (person != null) {
            model.addAttribute(person);
            model.addAttribute("isReservedView", false);
            return "person/selectedPerson.html";
        }
        return "redirect:/error.html";
    }

    @GetMapping("/person/{id}/reserve")
    public String reserve(@PathVariable("id") long id, Model model,
                          @RequestParam(required = false) String searchTitle) {
        Person person = personDAO.getById(id);
        if (person != null) {
            model.addAttribute("person", person);
            model.addAttribute("isReservedView", true);
            if (StringUtils.hasText(searchTitle)) {
                model.addAttribute("books", bookDAO.findBook(searchTitle));
            } else {
                model.addAttribute("books", bookDAO.getAllAvailableNotDeletedBooks(PageRequest.of(0, 50)));
            }
            return "person/selectedPerson.html";
        }
        return "redirect:/error.html";
    }

    @GetMapping("/person/{id}/reserve/book/{bookId}")
    public String reserveProcess(@PathVariable("id") long id, @PathVariable("bookId") long bookId) {
        Optional<Book> bookOptional = bookDAO.findById(bookId);
        Book book = null;
        if(bookOptional.isPresent()) {
            book = bookOptional.get();
        }
        Person person = personDAO.getById(id);
        if (person != null && book != null) {
            book.setBookStatus(BookStatus.BORROWED);
            book.setBookReserved(new BookReserved(person, book));
           // book.addBookReservedHistory(new BookReservedHistory(person, book, BookActionStatus.BOOK_BORROWED));
            bookDAO.insertBook(book);
        }
        return "redirect:/person/{id}/reserve";
    }


    @GetMapping("/person/{id}/given/book/{bookId}")
    public String givenProcess(@PathVariable("id") long id, @PathVariable("bookId") long bookId) {
        Optional<Book> bookOptional = bookDAO.findById(bookId);
        Book book = null;
        if(bookOptional.isPresent()) {
            book = bookOptional.get();
        }
        Person person = personDAO.getById(id);
        if (person != null && book != null) {
            BookReserved bookReserved = bookReservedDAO.getById(book.getBookReserved().getBookReservedId());
            book.setBookStatus(BookStatus.AVAILABLE);
            book.setBookReserved(null);
            bookDAO.insertBook(book);
            bookReservedDAO.delete(bookReserved);
        }
        return "redirect:/person/{id}/reserve";
    }
}

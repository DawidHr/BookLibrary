package com.dawidhr.BookLibrary.controller;


import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.dao.BookReservedDAO;
import com.dawidhr.BookLibrary.dao.PersonDAO;
import com.dawidhr.BookLibrary.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class PersonController {

    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private BookReservedDAO bookReservedDAO;
    private static final Integer PERSON_PER_PAGE = 5;

    @GetMapping("/persons")
    public String getAllPersons(Model model, HttpServletRequest request,
                                @RequestParam(required = false, defaultValue = "0") Integer page) {
        List<Integer> pagination = new ArrayList<>();
        Map<String, String[]> parameters = request.getParameterMap();
        model.addAttribute("persons", personDAO.getAll(PageRequest.of(page, PERSON_PER_PAGE)));
        preparePagination(pagination);
        model.addAttribute("pagination", pagination);
        return "person/list.html";
    }

    private void preparePagination(List<Integer> pagination) {
        int bookSize = personDAO.count();
        if (bookSize>0) {
            int pages = (int) Math.ceil(bookSize/PERSON_PER_PAGE);
            for(int i=0; i < pages; i++) {
                pagination.add(i);
            }
        }
    }

    @GetMapping("/person/add")
    public String addPersonForm(Model model) {
        model.addAttribute("person", new Person());
        return "person/add.html";
    }

    @PostMapping("/person/processAddingPerson")
    public String processAddingPerson(@Valid @ModelAttribute Person person, BindingResult bindingResult) {
        //TODO check if person exist
        // Add more validations
        if (bindingResult.hasErrors()) {
            return "person/add.html";
        }
        personDAO.insert(person);
        return "redirect:/persons";
    }

    @GetMapping("/person/{id}/view")
    public String show(@PathVariable("id") long id, Model model) {
        Person person = personDAO.getById(id);
        if (person != null) {
            model.addAttribute(person);
            return "person/selectedPerson.html";
        }
        return "redirect:/error.html";
    }

    @GetMapping("/person/{id}/reserve")
    public String reserve(@PathVariable("id") long id, Model model, HttpServletRequest request) {
        Person person = personDAO.getById(id);
        if (person != null) {
            String searchTitle = request.getParameter("bookTitle");
            model.addAttribute("person", person);
            if (searchTitle != null) {
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

package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "Books/List.html";
    }
}

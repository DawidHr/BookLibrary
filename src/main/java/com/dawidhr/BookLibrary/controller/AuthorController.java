package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.AuthorDAO;
import com.dawidhr.BookLibrary.model.Author;
import com.dawidhr.BookLibrary.model.Book;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthorController {

    private final AuthorDAO authorDAO;

    public AuthorController(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @RequestMapping("/authors")
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", authorDAO.getAllAuthors());
        return "author/list.html";
    }


    @GetMapping("/author/{id}")
    public String getSelectedAuthor(@PathVariable Long id, Model model) {
        Optional<Author> author = authorDAO.getAuthorById(id);
        if (author.isPresent()) {
            model.addAttribute("author",author.get());
            return "author/selectedAuthor.html";
        } else {
            throw new RuntimeException("Author not found");
        }
    }

    @GetMapping("/author/add")
    public String addAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "author/add.html";
    }

    @PostMapping("/author/process")
    public String processAddingAuthor(@Valid @ModelAttribute Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "author/add.html";
        }
        authorDAO.insertAuthor(author);
        return "author/list";
    }

}

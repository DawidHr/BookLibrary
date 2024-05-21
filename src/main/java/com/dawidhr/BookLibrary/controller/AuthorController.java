package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.AuthorDAO;
import com.dawidhr.BookLibrary.model.Author;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AuthorController {

    private final AuthorDAO authorDAO;
    private static final Integer AUTHOR_PER_PAGE = 2;

    public AuthorController(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @RequestMapping("/authors")
    public String getAllAuthors(Model model, HttpServletRequest request) {
        Integer page = 0;
        List<Long> pagination = new ArrayList<>();
        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters.containsKey("page")) {
            page = Integer.valueOf(parameters.get("page")[0]);
        }
        model.addAttribute("authors", authorDAO.getAllAuthors(PageRequest.of(page, AUTHOR_PER_PAGE)));
        preparePagination(pagination);
        model.addAttribute("pagination", pagination);
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

    @PostMapping("/author/findAuthor")
    public String findAuthor(Model model, HttpServletRequest request) {
        String searchAuthor = request.getParameter("searchAuthor");
        model.addAttribute("authors", authorDAO.findAuthor(searchAuthor));
        return "author/list.html";
    }

    private void preparePagination(List<Long> pagination) {
        long bookSize = authorDAO.count();
        if (bookSize>0) {
            long pages = (long) Math.ceil((double)bookSize/(double)AUTHOR_PER_PAGE);
            for(long i=0; i < pages; i++) {
                pagination.add(i);
            }
        }
    }

}

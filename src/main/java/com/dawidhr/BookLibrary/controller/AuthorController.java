package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.AuthorDAO;
import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.model.Author;
import com.dawidhr.BookLibrary.model.Book;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AuthorController {

    private final AuthorDAO authorDAO;
    private final BookDAO bookDAO;
    private static final Integer AUTHOR_PER_PAGE = 2;

    public AuthorController(AuthorDAO authorDAO, BookDAO bookDAO) {
        this.authorDAO = authorDAO;
        this.bookDAO = bookDAO;
    }

    @RequestMapping("/authors")
    public String getAllAuthors(Model model, HttpServletRequest request) {
        Integer page = 0;
        List<Long> pagination = new ArrayList<>();
        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters.containsKey("page")) {
            page = Integer.valueOf(parameters.get("page")[0]);
        }
        String searchAuthor = request.getParameter("searchAuthor");
        if (StringUtils.hasText(searchAuthor)) {
            model.addAttribute("authors", authorDAO.findAuthor(searchAuthor));
        } else {
            model.addAttribute("authors", authorDAO.getAllAuthors(PageRequest.of(page, AUTHOR_PER_PAGE)));
            preparePagination(pagination);
        }
        model.addAttribute("pagination", pagination);
        return "author/list.html";
    }


    @GetMapping("/author/{id}")
    public String getSelectedAuthor(@PathVariable Long id, Model model) {
        Optional<Author> author = authorDAO.findById(id);
        if (author.isPresent()) {
            model.addAttribute("author",author.get());
            return "author/selectedAuthor.html";
        } else {
            return "redirect:/error.html";
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

    @GetMapping("/author/{id}/edit")
    public String editSelectedAuthor(@PathVariable Long id, Model model,HttpServletRequest request) {
        Optional<Author> author = authorDAO.findById(id);
        if (author.isPresent()) {
            model.addAttribute("author",author.get());
            String bookTitle = request.getParameter("bookTitle");
            if (StringUtils.hasText(bookTitle)) {
                model.addAttribute("books",bookDAO.findBook(bookTitle));
            } else {
                model.addAttribute("books", bookDAO.getAllAvailableBooks(PageRequest.of(0, 5)));
            }

            return "author/editAuthor.html";
        } else {
            return "redirect:/error.html";
        }
    }

    @GetMapping("/author/{authorId}/book/{bookId}/add")
    public String assingBookToAuthor(@PathVariable("authorId") long authorId, @PathVariable("bookId") long bookId) {
        Optional<Author> author = authorDAO.findById(authorId);
        Optional<Book> book = bookDAO.findById(bookId);
        if (isBookAndAuthorExist(book, author)) {
            Author selectedAuthor = author.get();
            selectedAuthor.addBook(book.get());
            authorDAO.insertAuthor(selectedAuthor);
            return "redirect:/author/{authorId}";
        }
        return "redirect:/error.html";
    }

    private boolean isBookAndAuthorExist(Optional<Book> book, Optional<Author> author) {
        return book.isPresent() && author.isPresent();
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

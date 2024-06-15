package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.model.BookCategory;
import com.dawidhr.BookLibrary.model.BookStatus;
import com.dawidhr.BookLibrary.repository.BookRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BookController {

    private final BookDAO bookDAO;
    private static final Integer BOOK_PER_PAGE = 5;

    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping("/books")
    public String getAllBooks(Model model, HttpServletRequest request) {
        Integer page = 0;
        List<Integer> pagination = new ArrayList<>();
        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters.containsKey("page")) {
            page = Integer.valueOf(parameters.get("page")[0]);
        }

        model.addAttribute("books", bookDAO.getAllAvailableBooks(PageRequest.of(page, BOOK_PER_PAGE)));
        preparePagination(pagination);
        model.addAttribute("pagination", pagination);
        return "book/List.html";
    }



    @GetMapping("/book/{id}")
    public String getSelectedBook(@PathVariable Long id, Model model) {
        Optional<Book> book = bookDAO.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book",book.get());
            return "book/selectedBook.html";
        } else {
            return "redirect:error.html";
        }
    }

    @GetMapping("/book/{id}/edit")
    public String editSelectedBook(@PathVariable Long id, Model model) {
        Optional<Book> book = bookDAO.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book",book.get());
            model.addAttribute("category", Arrays.stream(BookCategory.values()).toList());
            model.addAttribute("status", Arrays.stream(BookStatus.values()).toList());
            return "book/editBook.html";
        } else {
            return "redirect:error.html";
        }
    }

    @PostMapping("book/saveEditBook")
    public String saveEditBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/editBook.html";
        }
        bookDAO.insertBook(book);
        return "book/List.html";
    }

    @GetMapping("/book/add")
    public String addBooks(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("category", Arrays.stream(BookCategory.values()).toList());
        model.addAttribute("status", Arrays.stream(BookStatus.values()).toList());
        return "book/add.html";
    }

    @PostMapping("/book/processAddingBook")
    public String processAddingBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/add.html";
        }
        bookDAO.insertBook(book);
        return "book/List.html";
    }

    @GetMapping("/book/{id}/delete")
    public String processAddingBook(@PathVariable Long id) {
        Optional<Book> optionalBook = bookDAO.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setDeleted(true);
            bookDAO.insertBook(book);
        }
        return "book/List.html";
    }

    @PostMapping("/book/findBook")
    public String findBook(Model model, HttpServletRequest request) {
        String bookTitle = request.getParameter("bookTitle");
        System.out.println(bookTitle);
        model.addAttribute("books", bookDAO.findBook(bookTitle));
        return "book/List.html";
    }

    private void preparePagination(List<Integer> pagination) {
        long bookSize = bookDAO.countAllAvailableBooks();
        if (bookSize>0) {
            int pages = (int) Math.ceil((double)bookSize/(double)BOOK_PER_PAGE);
            for(int i=0; i < pages; i++) {
                pagination.add(i);
            }
        }
    }
}

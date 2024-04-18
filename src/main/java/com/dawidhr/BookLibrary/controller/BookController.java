package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "book/List.html";
    }

    @GetMapping("/book/{id}")
    public String getSelectedBook(@PathVariable Long id, Model model) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book",book.get());
            return "book/selectedBook.html";
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    @GetMapping("book/add")
    public String addBooks() {
        return "book/add.html";
    }

    @PostMapping("/book/add")
    public void insertBook(Book book) {
        bookRepository.save(book);
    }
}

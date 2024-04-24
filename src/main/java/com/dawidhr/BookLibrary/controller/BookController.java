package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.repository.BookRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private static final Integer BOOK_PER_PAGE = 5;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public String getAllBooks(Model model, HttpServletRequest request) {
        Integer page = 0;
        List<Integer> pagination = new ArrayList<>();
        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters.containsKey("page")) {
            page = Integer.valueOf(parameters.get("page")[0]);
        }
        model.addAttribute("books", bookRepository.findAll(PageRequest.of(page, BOOK_PER_PAGE)));
        preparePagination(pagination);
        model.addAttribute("pagination", pagination);
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

    private void preparePagination(List<Integer> pagination) {
        int bookSize = bookRepository.findAll().size();
        if (bookSize>0) {
            int pages = (int) Math.ceil(bookSize/BOOK_PER_PAGE);
            for(int i=0; i < pages; i++) {
                pagination.add(i);
            }
        }
    }
}

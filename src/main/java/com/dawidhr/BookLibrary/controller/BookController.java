package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.model.BookStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookDAO bookDAO;
    private static final Integer BOOK_PER_PAGE = 5;

    @GetMapping("/books")
    public String getAllBooks(Model model, HttpServletRequest request) {
        int page = 0;
        List<Integer> pagination = new ArrayList<>();
        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters.containsKey("page")) {
            page = Integer.valueOf(parameters.get("page")[0]);
        }
        String bookSearchTitle = request.getParameter("bookTitle");
        if (StringUtils.hasText(bookSearchTitle)) {
            model.addAttribute("books", bookDAO.findBook(bookSearchTitle));
        } else {
            preparePagination(pagination);
            model.addAttribute("books", bookDAO.getAllAvailableBooks(PageRequest.of(page, BOOK_PER_PAGE)));
        }

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

    @GetMapping("/book/{id}/delete")
    public String processAddingBook(@PathVariable Long id) {
        Optional<Book> optionalBook = bookDAO.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (BookStatus.AVAILABLE.equals(book.getBookStatus())) {
                book.setDeleted(true);
                bookDAO.insertBook(book);
            }
        }
        return "redirect:/books";
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

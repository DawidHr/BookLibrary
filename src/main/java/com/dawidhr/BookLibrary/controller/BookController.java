package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.model.BookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookDAO bookDAO;
    private static final Integer BOOK_PER_PAGE = 5;
    private Integer[] listSizesAvailable = {2, 5, 10, 15, 20};

    @GetMapping("/books")
    public String getAllBooks(Model model, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "5") Integer listSize, @RequestParam(required = false) String bookTitle) {
        int productSizeList = BOOK_PER_PAGE;
        List<Integer> pagination = new ArrayList<>();
        if (isPageSizeAvailable(listSize)) {
            productSizeList = listSize;
        }
        String bookSearchTitle = bookTitle;
        if (StringUtils.hasText(bookSearchTitle)) {
            model.addAttribute("books", bookDAO.findBook(bookSearchTitle));
        } else {
            preparePagination(pagination, productSizeList);
            model.addAttribute("books", bookDAO.getAllAvailableBooks(PageRequest.of(page, productSizeList)));
        }

        model.addAttribute("pagination", pagination);
        model.addAttribute("listSize", listSize);
        model.addAttribute("listSizeAvailable", listSizesAvailable);
        return "book/List.html";
    }

    private boolean isPageSizeAvailable(int listSize) {
        return Arrays.stream(listSizesAvailable).anyMatch(page -> page.equals(listSize));
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

    private void preparePagination(List<Integer> pagination, int listSize) {
        long bookSize = bookDAO.countAllAvailableBooks();
        if (bookSize>0) {
            int pages = (int) Math.ceil((double)bookSize/(double)listSize);
            for(int i=0; i < pages; i++) {
                pagination.add(i);
            }
        }
    }
}

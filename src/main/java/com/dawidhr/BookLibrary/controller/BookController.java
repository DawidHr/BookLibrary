package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.helper.ProductListPage;
import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookDAO bookDAO;
    @Autowired
    BookService bookService;

    @GetMapping("/books")
    public String getAllBooks(Model model, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "5") Integer listSize, @RequestParam(required = false) String bookTitle) {
        int productSizeList = ProductListPage.DEFAULT_PER_PAGE;
        List<Long> pagination = new LinkedList<>();
        if (ProductListPage.isPageSizeAvailable(listSize)) {
            productSizeList = listSize;
        }
        if (StringUtils.hasText(bookTitle)) {
            model.addAttribute("books", bookDAO.findBook(bookTitle));
        } else {
            long bookSize = bookDAO.countAllAvailableBooks();
            pagination = ProductListPage.preparePagination(bookSize, productSizeList);
            model.addAttribute("books", bookDAO.getAllAvailableBooks(PageRequest.of(page, productSizeList)));
        }

        model.addAttribute("pagination", pagination);
        model.addAttribute("listSize", listSize);
        model.addAttribute("listSizeAvailable", ProductListPage.listSizesAvailable);
        return "book/List.html";
    }

    @GetMapping("/book/{id}")
    public String getSelectedBook(@PathVariable Long id, Model model) {
        Optional<Book> book = bookService.findBookById(id);
        if (book.isPresent()) {
            model.addAttribute("book",book.get());
            return "book/selectedBook.html";
        } else {
            return "redirect:error.html";
        }
    }

    @GetMapping("/book/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }
}

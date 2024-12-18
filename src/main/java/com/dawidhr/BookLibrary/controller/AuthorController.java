package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.AuthorDAO;
import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.dao.BookInfoDAOImpl;
import com.dawidhr.BookLibrary.helper.ProductListPage;
import com.dawidhr.BookLibrary.model.*;
import com.dawidhr.BookLibrary.model.simple.BookAddModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class AuthorController {
    @Autowired
    private AuthorDAO authorDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private BookInfoDAOImpl bookInfoDAO;

    @RequestMapping("/authors")
    public String getAllAuthors(Model model,
                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                @RequestParam(required = false, defaultValue = "5") Integer listSize,
                                @RequestParam(required = false) String search) {
        int productSizeList = ProductListPage.DEFAULT_PER_PAGE;
        List<Long> pagination = new ArrayList<>();
        if (ProductListPage.isPageSizeAvailable(listSize)) {
            productSizeList = listSize;
        }
        if (StringUtils.hasText(search)) {
            model.addAttribute("authors", authorDAO.findAuthor(search));
        } else {
            long bookSize = authorDAO.count();
            pagination = ProductListPage.preparePagination(bookSize, productSizeList);
            model.addAttribute("authors", authorDAO.getAllAuthors(PageRequest.of(page, productSizeList)));
        }
        model.addAttribute("pagination", pagination);
        model.addAttribute("listSize", listSize);
        model.addAttribute("listSizeAvailable", ProductListPage.listSizesAvailable);
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
        if(isAuthorExistsInDB(author)) {
            return "redirect:/authors";
        }
        authorDAO.insertAuthor(author);
        return "redirect:/authors";
    }

    private boolean isAuthorExistsInDB(Author author) {
        Author authorFromDb = authorDAO.findAuthor(author);
        return authorFromDb != null;
    }

    @GetMapping("/author/{id}/edit")
    public String editSelectedAuthor(@PathVariable Long id, Model model) {
        Optional<Author> author = authorDAO.findById(id);
        if (author.isPresent()) {
            model.addAttribute("author",author.get());
            return "author/editAuthor.html";
        } else {
            return "redirect:/error.html";
        }
    }

    @GetMapping("/author/{authorId}/book/add")
    public String addBookToAuthor(@PathVariable("authorId") long authorId, Model model) {
        return prepareAddingView(model, authorId);
    }

    @PostMapping("/author/{authorId}/book/add")
    public String processAddingBook(@Valid @ModelAttribute BookAddModel bookAddModel, BindingResult bindingResult, @PathVariable("authorId") long authorId, Model model) {
        if (bindingResult.hasErrors()) {
            return prepareAddingView(model, authorId);
        }
        BookInfo fundedBookInfo = bookInfoDAO.findByTitle(bookAddModel.getTitle());
        if(fundedBookInfo != null) {
            return "redirect:/error.html";
        }
        Optional<Author> author = authorDAO.findById(authorId);
        if (author.isPresent()) {
            BookInfo bookInfo = new BookInfo(bookAddModel);
            bookInfoDAO.insertBookInfo(bookInfo);
            long quantity = bookAddModel.getQuantity();
            Book book = new Book(author.get(), bookInfo);
            for (long i = 0; i < quantity ; i++) {
                bookDAO.insertBook(book);
            }
            Author author1 = author.get();
            author1.addBook(bookInfo);
            authorDAO.insertAuthor(author1);
            return "redirect:/author/"+authorId;
        }
        return "redirect:/error.html";
    }

    private String prepareAddingView(Model model, long authorId) {
        model.addAttribute("book", new BookAddModel());
        model.addAttribute("category", Arrays.stream(BookCategory.values()).toList());
        model.addAttribute("status", Arrays.stream(BookStatus.values()).toList());
        model.addAttribute("authorId", authorId);
        return "/book/add2.html";
    }

}

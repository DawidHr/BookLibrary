package com.dawidhr.BookLibrary.config;

import com.dawidhr.BookLibrary.dao.AuthorDAO;
import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.dao.BookInfoDAO;
import com.dawidhr.BookLibrary.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class Boot implements CommandLineRunner {

    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private AuthorDAO authorDAO;
    @Autowired
    private BookInfoDAO bookInfoDAO;


    @Override
    public void run(String... args) throws Exception {
        prepareDataForDatabase();
    }

    private void prepareDataForDatabase() {

        Author author1 = new Author("Andrzej", "Ziemiański");
        Author author2 = new Author("Williams Chima", "Cinda");
        Author author3 = new Author("Trudi", "Canavan");

        BookInfo book1 = new BookInfo("Virion. Wyrocznia. Tom 1", BookCategory.FANTASY, "https://i.ytimg.com/vi/zxkwAXVQoKc/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLDhwIoBMKyJzrxQSznlbC1wq8Qa6g");
        BookInfo book2 = new BookInfo("Virion. Obława. Tom 2", BookCategory.FANTASY, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQI2PZATo_BW9VYNF82K5vD4a5sS3W1xfkmc2iVq-z2zw&s");
        BookInfo book3 = new BookInfo("Virion. Adept. Tom 3", BookCategory.FANTASY, "https://ecsmedia.pl/c/virion-adept-tom-3-b-iext146705461.jpg");
        BookInfo book4 = new BookInfo("Virion. Szermierz. Tom 4", BookCategory.FANTASY, "");
        BookInfo book5 = new BookInfo("Virion. Zamek. Szermierz natchniony. Tom 1", BookCategory.FANTASY, "");
        BookInfo book6 = new BookInfo("Virion. Pustynia. Szermierz natchniony. Tom 2", BookCategory.FANTASY, "");
        BookInfo book7 = new BookInfo("Virion. Legion. Szermierz natchniony. Tom 3", BookCategory.FANTASY, "");

        BookInfo book8 = new BookInfo("Król Demon. Siedem królestw. Księga 1", BookCategory.FANTASY, "");
        BookInfo book9 = new BookInfo("Wygnana królowa. Siedem królestw. Księga 2", BookCategory.FANTASY, "");
        BookInfo book10 = new BookInfo("Karmazynowa korona. Siedem królestw. Księga 4", BookCategory.FANTASY, "");



        Book book1_1 = new Book(book1);
        Book book1_2 = new Book(book1);
        Book book1_3 = new Book(book1);

        Book book2_1 = new Book(book2);
        Book book2_2 = new Book(book2);

        book1.addBook(book1_1);
        book1.addBook(book1_2);
        book1.addBook(book1_3);

        book2.addBook(book2_1);
        book2.addBook(book2_2);

        author1.addBook(book1);
        author1.addBook(book2);
        author1.addBook(book3);
        author1.addBook(book4);
        author1.addBook(book5);
        author1.addBook(book6);
        author1.addBook(book7);

        author2.addBook(book8);
        author2.addBook(book9);
        author2.addBook(book10);

        List<Author> authors = new ArrayList<>();
        authors.add(author3);
        authors.add(author2);
        authors.add(author1);
        List<BookInfo> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);
        books.add(book7);
        books.add(book8);
        books.add(book9);
        books.add(book10);

        for (Author author : authors) {
            authorDAO.insertAuthor(author);
        }
        for(BookInfo book: books) {
            bookInfoDAO.insertBookInfo(book);
        }
    }
}

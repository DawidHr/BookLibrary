package com.dawidhr.BookLibrary.config;

import com.dawidhr.BookLibrary.dao.AuthorDAO;
import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.model.Author;
import com.dawidhr.BookLibrary.model.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Boot implements CommandLineRunner {

    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;

    public Boot(BookDAO bookDAO, AuthorDAO authorDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
    }

    @Override
    public void run(String... args) throws Exception {
        prepareDataForDatabase();
    }

    private void prepareDataForDatabase() {

        Author author1 = new Author("Andrzej", "Ziemiański");
        Author author2 = new Author("Williams Chima", "Cinda");
        Author author3 = new Author("Trudi", "Canavan");

        Book book1 = new Book("Virion. Wyrocznia. Tom 1");
        Book book2 = new Book("Virion. Obława. Tom 2");
        Book book3 = new Book("Virion. Adept. Tom 3");
        Book book4 = new Book("Virion. Szermierz. Tom 4");
        Book book5 = new Book("Virion. Zamek. Szermierz natchniony. Tom 1");
        Book book6 = new Book("Virion. Pustynia. Szermierz natchniony. Tom 2");
        Book book7 = new Book("Virion. Legion. Szermierz natchniony. Tom 3");

        Book book8 = new Book("Król Demon. Siedem królestw. Księga 1");
        Book book9 = new Book("Wygnana królowa. Siedem królestw. Księga 2");
        Book book10 = new Book("Karmazynowa korona. Siedem królestw. Księga 4");

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

        book1.addAuthor(author1);
        book2.addAuthor(author1);
        book3.addAuthor(author1);
        book4.addAuthor(author1);
        book5.addAuthor(author1);
        book6.addAuthor(author1);
        book7.addAuthor(author1);

        book8.addAuthor(author2);
        book9.addAuthor(author2);
        book10.addAuthor(author2);

        List<Author> authors = new ArrayList<>();
        authors.add(author3);
        authors.add(author2);
        authors.add(author1);
        List<Book> books = new ArrayList<>();
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
    }
}

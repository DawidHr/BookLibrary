package com.dawidhr.BookLibrary.config;

import com.dawidhr.BookLibrary.model.Author;
import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.repository.AuthorRepository;
import com.dawidhr.BookLibrary.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Boot implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public Boot(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        prepareAndInsertAuthors();
        prepareAndInsertBooks();
    }

    private void prepareAndInsertBooks() {
        List<Book> books = prepareListOfBooks();
        insertBooksToDatabase(books);
    }

    private void insertBooksToDatabase(List<Book> books) {
        for (Book book : books) {
            bookRepository.save(book);
        }
    }

    private List<Book> prepareListOfBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Virion. Wyrocznia. Tom 1"));
        books.add(new Book("Virion. Obława. Tom 2"));
        books.add(new Book("Virion. Adept. Tom 3"));
        books.add(new Book("Virion. Szermierz. Tom 4"));
        books.add(new Book("Virion. Zamek. Szermierz natchniony. Tom 1"));
        books.add(new Book("Virion. Pustynia. Szermierz natchniony. Tom 2"));
        books.add(new Book("Virion. Legion. Szermierz natchniony. Tom 3"));

        books.add(new Book("Król Demon. Siedem królestw. Księga 1"));
        books.add(new Book("Wygnana królowa. Siedem królestw. Księga 2"));
        books.add(new Book("Karmazynowa korona. Siedem królestw. Księga 4"));
        return books;
    }

    private void prepareAndInsertAuthors() {
        List<Author> authors = prepareListOfAuthors();
        insertAuthorsToDatabase(authors);
    }

    private List<Author> prepareListOfAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Andrzej", "Ziemiański"));
        authors.add(new Author("Williams Chima", "Cinda"));
        authors.add(new Author("Trudi", "Canavan"));
        return authors;
    }

    private void insertAuthorsToDatabase(List<Author> authors) {
        for (Author author : authors) {
            authorRepository.save(author);
        }
    }
}

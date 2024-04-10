package com.dawidhr.BookLibrary.config;

import com.dawidhr.BookLibrary.model.Author;
import com.dawidhr.BookLibrary.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Boot implements CommandLineRunner {

    private final AuthorRepository authorRepository;

    public Boot(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        prepareAndInsertAuthors();
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

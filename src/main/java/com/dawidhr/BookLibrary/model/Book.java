package com.dawidhr.BookLibrary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "Book")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    @Column(name = "title")
    private String title;
    @ManyToMany()
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();
    private String description;
    @Enumerated(EnumType.STRING)
    private BookCategory category;
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    public Book(String title, String description, BookCategory category, BookStatus bookStatus) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.bookStatus = bookStatus;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

}
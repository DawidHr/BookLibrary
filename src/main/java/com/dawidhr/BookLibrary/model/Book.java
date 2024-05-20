package com.dawidhr.BookLibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    @NotBlank
    @Length(max=200)
    private String title;
    @ManyToMany()
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    @NotNull
    private BookCategory category;
    @Enumerated(EnumType.STRING)
    @Column(name = "book_status")
    private BookStatus bookStatus;
    @Column(name = "image_url")
    private String imageUrl;

    public Book(String title, String description, BookCategory category, BookStatus bookStatus, String imageUrl) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.bookStatus = bookStatus;
        this.imageUrl = imageUrl;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

}
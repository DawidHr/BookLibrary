package com.dawidhr.BookLibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "Author")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "first_name")
    @NotBlank(message = "First Name is required")
    private String firstName;
    @Column(name = "last_name")
    @NotBlank(message = "Last Name is required")
    private String lastName;
    @ManyToMany(mappedBy = "authors", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<BookInfo> books = new HashSet<>();
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp creationDate;
    @UpdateTimestamp
    private Timestamp modificationDate;
    private String imageUrl;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addBook(BookInfo book) {
        this.books.add(book);
        book.addAuthor(this);
    }
}

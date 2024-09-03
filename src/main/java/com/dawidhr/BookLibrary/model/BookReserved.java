package com.dawidhr.BookLibrary.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.Instant;

@NoArgsConstructor
@Data
@Entity
@Table(name = "book_reserved")
public class BookReserved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_reserved_id")
    private Long bookReservedId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName="book_id")
    private Book book;
    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;

    public BookReserved(Person person, Book book) {
        this.person = person;
        this.book = book;
        this.creationDate = Timestamp.from(Instant.now());
    }
}

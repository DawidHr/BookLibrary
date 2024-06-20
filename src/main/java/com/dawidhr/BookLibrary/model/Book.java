package com.dawidhr.BookLibrary.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity()
@Table(name = "Book")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;
    @ManyToOne
    BookInfo bookInfo;
    @Enumerated(EnumType.STRING)
    @Column(name = "book_status")
    private BookStatus bookStatus;
    @Column(name = "is_deleted")
    boolean isDeleted;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp creationDate;
    @UpdateTimestamp
    private Timestamp modificationDate;

    public Book(BookInfo bookInfo, BookStatus bookStatus, boolean isDeleted, Timestamp creationDate, Timestamp modificationDate) {
        this.bookInfo = bookInfo;
        this.bookStatus = bookStatus;
        this.isDeleted = isDeleted;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    public Book(Author author, BookInfo bookInfo) {
        this.bookInfo = bookInfo;
        this.bookStatus = BookStatus.AVAILABLE;
        this.isDeleted = false;
    }

    public Book(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
        this.bookStatus = BookStatus.AVAILABLE;
        this.isDeleted = false;
    }

}
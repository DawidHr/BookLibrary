package com.dawidhr.BookLibrary.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

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
    private Person person;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName="book_id")
    private Book book;
    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;
}

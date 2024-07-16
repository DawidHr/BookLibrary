package com.dawidhr.BookLibrary.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "book_reserved_history")
public class BookReservedHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_reserved_history_id")
    private long bookReservedHistoryId;
    @ManyToOne
    private Person person;
    @ManyToOne
    private Book book;
    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private Timestamp creationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "book_action_status")
    BookActionStatus bookActionStatus;
}

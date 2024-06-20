package com.dawidhr.BookLibrary.model;


import com.dawidhr.BookLibrary.model.simple.BookAddModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "book_info")
public class BookInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_info_id")
    private Long bookInfoId;
    @ManyToMany()
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;
    @Column(name = "title")
    @NotBlank(message = "Title is required")
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    @NotNull
    private BookCategory category;
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @OneToMany(mappedBy = "bookInfo")
    List<Book> books = new ArrayList<>();

    public BookInfo(BookAddModel bookAddModel) {
        this.title = bookAddModel.getTitle();
        this.category = bookAddModel.getCategory();
        this.description = bookAddModel.getDescription();
        this.imageUrl = bookAddModel.getImageUrl();
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }
}

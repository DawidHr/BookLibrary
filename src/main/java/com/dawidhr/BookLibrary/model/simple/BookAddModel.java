package com.dawidhr.BookLibrary.model.simple;

import com.dawidhr.BookLibrary.model.BookCategory;
import com.dawidhr.BookLibrary.model.BookStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BookAddModel {
    @NotBlank(message = "Title is required")
    private String title;
    @Column(name = "description")
    private String description;
    @NotNull
    private BookCategory category;
    @NotNull
    private BookStatus bookStatus;
    private String imageUrl;
    private Long quantity;
}
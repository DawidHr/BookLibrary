package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class BookControllerTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    BookDAO bookDAO;

    @InjectMocks
    BookController bookController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void bookListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attributeExists("pagination"))
                .andExpect(view().name("book/List.html"));
    }

    @Test
    public void bookSelectedTest() throws Exception {
        Optional<Book> book = Optional.of(new Book());
        when(bookRepository.findById(any())).thenReturn(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/book/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("book/selectedBook.html"));
    }

    @Test
    public void bookEditTest() throws Exception {
        Optional<Book> book = Optional.of(new Book());
        when(bookRepository.findById(any())).thenReturn(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/book/{id}/edit", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attributeExists("status"))
                .andExpect(view().name("book/editBook.html"));
    }

}
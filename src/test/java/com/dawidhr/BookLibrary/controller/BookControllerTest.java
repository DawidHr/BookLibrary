package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.model.BookCategory;
import com.dawidhr.BookLibrary.model.BookStatus;
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
    public void bookSelectedNotFoundTest() throws Exception {
        Optional<Book> book = Optional.empty();
        when(bookRepository.findById(any())).thenReturn(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/book/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:error.html"));
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

    @Test
    public void bookEditNotFoundBookTest() throws Exception {
        Optional<Book> book = Optional.empty();
        when(bookRepository.findById(any())).thenReturn(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/book/{id}/edit", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:error.html"));
    }

    @Test
    public void bookSaveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/book/saveEditBook")
                        .param("title", "Test")
                        .param("category", BookCategory.FANTASY.name())
                        .param("bookStatus", BookStatus.AVAILABLE.name()))
                .andExpect(status().isOk())
                .andExpect(view().name("book/List.html"));
    }

    @Test
    public void bookSaveNotValidTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/book/saveEditBook")
                        .param("title", "Test")
                        .param("bookStatus", BookStatus.AVAILABLE.name()))
                .andExpect(status().isOk())
                .andExpect(view().name("book/editBook.html"));
    }

    @Test
    public void bookAddTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("category"))
                .andExpect(model().attributeExists("status"))
                .andExpect(view().name("book/add.html"));
    }

    @Test
    public void bookProcessAddingBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/book/processAddingBook")
                .param("title", "Test")
                .param("category", BookCategory.FANTASY.name())
                .param("bookStatus", BookStatus.AVAILABLE.name()))
                .andExpect(status().isOk())
                .andExpect(view().name("book/List.html"));
    }

    @Test
    public void bookProcessAddingBookNotValidTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/book/processAddingBook")
                        .param("title", "Test")
                        .param("bookStatus", BookStatus.AVAILABLE.name()))
                .andExpect(status().isOk())
                .andExpect(view().name("book/add.html"));
    }

}
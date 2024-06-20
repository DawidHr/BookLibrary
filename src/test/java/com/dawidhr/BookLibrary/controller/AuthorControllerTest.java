package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.AuthorDAO;
import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Disabled
class AuthorControllerTest {

    @Mock
    AuthorDAO authorDAO;

    @Mock
    BookDAO bookDAO;

    @InjectMocks
    AuthorController authorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    public void authorListTest() throws Exception {
        when(authorDAO.getAllAuthors(any())).thenReturn(Page.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("pagination"))
                .andExpect(view().name("author/list.html"));
    }

    @Test
    public void authorSelectedTest() throws Exception {
        Optional<Author> author = Optional.of(new Author());
        when(authorDAO.findById(any())).thenReturn(author);
        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("author/selectedAuthor.html"));
    }

    @Test
    public void authorSelectedNotFoundTest() throws Exception {
        Optional<Author> author = Optional.empty();
        when(authorDAO.findById(any())).thenReturn(author);
        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", 1))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/error.html"));
    }

    @Test
    public void authorEditTest() throws Exception {
        Optional<Author> author = Optional.of(new Author());
        when(authorDAO.findById(any())).thenReturn(author);
        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}/edit", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("author/editAuthor.html"));
    }

    @Test
    public void authorEditNotFoundTest() throws Exception {
        Optional<Author> author = Optional.empty();
        when(authorDAO.findById(any())).thenReturn(author);
        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}/edit", 1))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/error.html"));
    }

    @Test
    public void authorAddTest() throws Exception {
        Optional<Author> author = Optional.empty();
        when(authorDAO.findById(any())).thenReturn(author);
        mockMvc.perform(MockMvcRequestBuilders.get("/author/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("author/add.html"));
    }

}
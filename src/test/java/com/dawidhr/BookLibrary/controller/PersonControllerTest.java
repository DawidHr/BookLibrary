package com.dawidhr.BookLibrary.controller;

import com.dawidhr.BookLibrary.dao.BookDAO;
import com.dawidhr.BookLibrary.dao.PersonDAO;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class PersonControllerTest {

    @Mock
    PersonDAO personDAO;
    @Mock
    BookDAO bookDAO;

    @InjectMocks
    PersonController personController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void personListTest() throws Exception {
        when(personDAO.getAll(any())).thenReturn(Page.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/persons"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("persons"))
                .andExpect(model().attributeExists("pagination"))
                .andExpect(view().name("person/list.html"));
    }

    @Test
    public void personAddTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/person/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("person"))
                .andExpect(view().name("person/add.html"));
    }
}
package com.dawidhr.BookLibrary.controller;


import com.dawidhr.BookLibrary.repository.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PersonController {

    private final PersonRepository personRepository;
    private static final Integer PERSON_PER_PAGE = 5;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/persons")
    public String getAllBooks(Model model, HttpServletRequest request) {
        Integer page = 0;
        List<Integer> pagination = new ArrayList<>();
        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters.containsKey("page")) {
            page = Integer.valueOf(parameters.get("page")[0]);
        }
        model.addAttribute("books", personRepository.findAll(PageRequest.of(page, PERSON_PER_PAGE)));
        preparePagination(pagination);
        model.addAttribute("pagination", pagination);
        return "person/list.html";
    }

    private void preparePagination(List<Integer> pagination) {
        int bookSize = personRepository.findAll().size();
        if (bookSize>0) {
            int pages = (int) Math.ceil(bookSize/PERSON_PER_PAGE);
            for(int i=0; i < pages; i++) {
                pagination.add(i);
            }
        }
    }
}

package com.dawidhr.BookLibrary.controller;


import com.dawidhr.BookLibrary.dao.PersonDAO;
import com.dawidhr.BookLibrary.model.Person;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PersonController {

    private final PersonDAO personDAO;
    private static final Integer PERSON_PER_PAGE = 5;

    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("/persons")
    public String getAllPersons(Model model, HttpServletRequest request) {
        Integer page = 0;
        List<Integer> pagination = new ArrayList<>();
        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters.containsKey("page")) {
            page = Integer.valueOf(parameters.get("page")[0]);
        }
        model.addAttribute("persons", personDAO.getAll(PageRequest.of(page, PERSON_PER_PAGE)));
        preparePagination(pagination);
        model.addAttribute("pagination", pagination);
        return "person/list.html";
    }

    private void preparePagination(List<Integer> pagination) {
        int bookSize = personDAO.count();
        if (bookSize>0) {
            int pages = (int) Math.ceil(bookSize/PERSON_PER_PAGE);
            for(int i=0; i < pages; i++) {
                pagination.add(i);
            }
        }
    }

    @GetMapping("/person/add")
    public String addPersonForm(Model model) {
        model.addAttribute("person", new Person());
        return "person/add.html";
    }

    @PostMapping("/person/processAddingPerson")
    public String processAddingPerson(@Valid @ModelAttribute Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "person/add.html";
        }
        personDAO.insert(person);
        return "person/list";
    }

    @GetMapping("/person/{id}/view")
    public String show(@PathVariable("id") long id, Model model) {
        Person person = personDAO.getById(id);
        if (person != null) {
            model.addAttribute(person);
            return "person/selectedPerson.html";
        }
        return "redirect:/error.html";
    }

    @GetMapping("/person/{id}/reserve")
    public String reserve(@PathVariable("id") long id, Model model) {
        Person person = personDAO.getById(id);
        if (person != null) {
            model.addAttribute(person);
            return "person/selectedPerson.html";
        }
        return "redirect:/error.html";
    }
}

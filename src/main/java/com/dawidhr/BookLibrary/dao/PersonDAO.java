package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Person;
import com.dawidhr.BookLibrary.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonDAO {
    void insert(Person person);
    Person getById(Long id);
    List<Person> getAll();
    Page<Person> getAll(Pageable pageable);
    void update(Person person);
    int count();
    long countPersonRegisteredInLast30Days();
    List<Person> findPerson(String search);
}

package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Person;
import com.dawidhr.BookLibrary.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PersonDAOImpl implements PersonDAO {

    private PersonRepository personRepository;

    public PersonDAOImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void insert(Person person) {
        personRepository.save(person);
    }

    @Override
    public Person getById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public Page<Person> getAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public void update(Person person) {
        personRepository.save(person);
    }
}

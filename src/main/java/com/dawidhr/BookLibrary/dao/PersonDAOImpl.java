package com.dawidhr.BookLibrary.dao;

import com.dawidhr.BookLibrary.model.Book;
import com.dawidhr.BookLibrary.model.Person;
import com.dawidhr.BookLibrary.repository.PersonRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class PersonDAOImpl implements PersonDAO {

    private PersonRepository personRepository;
    private final EntityManagerFactory entityManagerFactory;

    public PersonDAOImpl(PersonRepository personRepository, EntityManagerFactory entityManagerFactory) {
        this.personRepository = personRepository;
        this.entityManagerFactory = entityManagerFactory;
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

    @Override
    public int count() {
        return personRepository.findAll().size();
    }

    @Override
    public long countPersonRegisteredInLast30Days() {
        TypedQuery<Long> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT count(b) FROM Person b WHERE b.creationDate > :last30days 
                """, Long.class);
        query.setParameter("last30days",new Timestamp(System.currentTimeMillis()).toLocalDateTime().minusMonths(1));
        return query.getSingleResult();
    }

    @Override
    public List<Person> findPerson(String search) {
        TypedQuery<Person> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT p FROM Person p WHERE p.firstName like :search or p.lastName like :search or p.pesel like :search 
                """, Person.class);
        query.setParameter("search", "%"+search+"%");
        return query.getResultList();
    }
}

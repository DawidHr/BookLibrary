package com.dawidhr.BookLibrary.repository;

import com.dawidhr.BookLibrary.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}

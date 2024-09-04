package com.dawidhr.BookLibrary.dao;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PersonNotificationDAOImpl implements PersonNotificationDAO {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public boolean isPersonNotifiedIn7Days(long personId) {
        TypedQuery<Boolean> query = entityManagerFactory.createEntityManager().createQuery("""
                SELECT * FROM PersonNotificationHistory p WHERE p.creationDate >= :creationDate AND p.Person.personId = :personId
                """, Boolean.class);
        query.setParameter("creationDate", Timestamp.from(Instant.now().minus(7, ChronoUnit.DAYS)));
        query.setParameter("personId", personId);
        return query.getSingleResult();
    }
}

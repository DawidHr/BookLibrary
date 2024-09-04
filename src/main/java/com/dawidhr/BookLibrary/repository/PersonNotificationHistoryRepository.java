package com.dawidhr.BookLibrary.repository;

import com.dawidhr.BookLibrary.model.PersonNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonNotificationHistoryRepository extends JpaRepository<PersonNotificationHistory, Long> {
}

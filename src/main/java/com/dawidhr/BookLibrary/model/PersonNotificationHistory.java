package com.dawidhr.BookLibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonNotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long personNotificationHistoryId;
    private Long personId;
    @CreationTimestamp
    private Timestamp creationDate;
}

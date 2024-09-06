package com.dawidhr.BookLibrary.repository;

import com.dawidhr.BookLibrary.model.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Settings, Long> {
}

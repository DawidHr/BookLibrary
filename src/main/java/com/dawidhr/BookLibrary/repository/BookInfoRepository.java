package com.dawidhr.BookLibrary.repository;

import com.dawidhr.BookLibrary.model.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInfoRepository extends JpaRepository<BookInfo, Long> {
}

package com.dawidhr.BookLibrary.model.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatisticType {
    AUTHOR_DASHBOARD("AuthorDashboard", AuthorDashboard.class),
    BOOK_DASHBOARD("BookDashboard", BookDashboard.class),
    BOOK_RESERVED_DASHBOARD("BookReservedDashboard", BookReservedDashboard.class),
    PERSON_DASHBOARD("PersonDashboard", PersonDashboard.class);

    private String title;
    private Object object;
}

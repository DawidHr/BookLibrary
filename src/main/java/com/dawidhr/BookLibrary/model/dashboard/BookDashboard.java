package com.dawidhr.BookLibrary.model.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDashboard {
    private long booksCount;
    private long booksAddedLast30daysCount;
}

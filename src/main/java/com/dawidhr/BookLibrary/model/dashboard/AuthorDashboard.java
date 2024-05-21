package com.dawidhr.BookLibrary.model.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDashboard {
    private long authorsCount;
    private long authorsAddedLast30daysCount;
}

package com.dawidhr.BookLibrary.model.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookReservedDashboard {
    private long bookReservedCount;
    private long bookReservedLast30daysCount;
}

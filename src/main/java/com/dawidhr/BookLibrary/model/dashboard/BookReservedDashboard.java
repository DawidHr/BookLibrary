package com.dawidhr.BookLibrary.model.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookReservedDashboard implements Serializable {
    private long bookReservedCount;
    private long bookReservedLast30daysCount;
}

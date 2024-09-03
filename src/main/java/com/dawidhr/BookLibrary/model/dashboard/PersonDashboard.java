package com.dawidhr.BookLibrary.model.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonDashboard {
    private long personRegisteredCount;
    private long personRegisteredLast30daysCount;
}

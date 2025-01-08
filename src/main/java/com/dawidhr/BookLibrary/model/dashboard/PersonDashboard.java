package com.dawidhr.BookLibrary.model.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonDashboard implements Serializable {
    private long personRegisteredCount;
    private long personRegisteredLast30daysCount;
}

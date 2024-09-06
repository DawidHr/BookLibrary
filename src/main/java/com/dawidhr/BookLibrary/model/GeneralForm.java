package com.dawidhr.BookLibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeneralForm {
    private long generalFormId;
    private boolean notificationStatus;
    private int notificationRate;
    private float price;

}

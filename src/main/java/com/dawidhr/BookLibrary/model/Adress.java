package com.dawidhr.BookLibrary.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Embeddable
public class Adress {
    private String street;
    private String houseNumber;
    private String zipCode;
    private String city;
}

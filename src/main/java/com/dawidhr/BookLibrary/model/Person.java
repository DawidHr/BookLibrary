package com.dawidhr.BookLibrary.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@AttributeOverrides( {
        @AttributeOverride(
                name = "adress.street",
                column = @Column(name = "adress_street")
        ),
        @AttributeOverride(
                name = "adress.houseNumber",
                column = @Column(name = "adress_house_number")
        ),
        @AttributeOverride(
                name = "adress.zipCode",
                column = @Column(name = "adress_zip_code")
        ),
        @AttributeOverride(
                name = "adress.city",
                column = @Column(name = "adress_city")
        )
})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "pesel")
    private Integer pesel;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    private Adress adress;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp creationDate;
    @UpdateTimestamp
    private Timestamp modificationDate;
}

package com.dawidhr.BookLibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


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
    @Column(name = "person_id")
    private Long personId;
    @Column(name = "first_name")
    @NotBlank(message = "First Name is required")
    private String firstName;
    @Column(name = "last_name")
    @NotBlank(message = "Last Name is required")
    private String lastName;
    @Column(name = "pesel")
    private String pesel;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    private Adress adress;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp creationDate;
    @UpdateTimestamp
    private Timestamp modificationDate;
    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private Set<BookReserved> bookReserves = new HashSet<>();
    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "book_reserved_history_id")
    private Set<BookReservedHistory> bookReservedHistories = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return pesel != null ? pesel.equals(person.pesel) : person.pesel == null;
    }

    @Override
    public int hashCode() {
        return pesel != null ? pesel.hashCode() : 0;
    }
}

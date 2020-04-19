package com.codecool.erpspringboot2.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;
    private LocalDate birthDate;
    private String address;
    private int salary;
    private LocalDate dateOfEmployment;

}

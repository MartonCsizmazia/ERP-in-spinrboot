package com.codecool.erpspringboot2.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Expense {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;
    private int value;
    private LocalDate date;

}

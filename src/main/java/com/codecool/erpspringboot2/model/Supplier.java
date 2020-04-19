package com.codecool.erpspringboot2.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Supplier {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;
    private String address;
    private int rating;

}

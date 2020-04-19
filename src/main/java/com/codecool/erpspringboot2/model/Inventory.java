package com.codecool.erpspringboot2.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Inventory {

    @Id
    @GeneratedValue
    private Long id;

    private long fakePrimaryKey;

    @Singular
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = {CascadeType.REMOVE})
    private List<Lineitem> stockLineitems = new ArrayList<>();
}
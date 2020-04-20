package com.codecool.erpspringboot2.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Stock {

    @Id
    @GeneratedValue
    private Long id;

    @Singular
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Lineitem> stockLineitems;

}

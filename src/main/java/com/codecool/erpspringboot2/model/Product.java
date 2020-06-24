package com.codecool.erpspringboot2.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String manufacturer;
    private int price;
    private double profit;

    @OneToOne(mappedBy = "product")
    private Lineitem lineitem;

}

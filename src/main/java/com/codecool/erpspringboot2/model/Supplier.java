package com.codecool.erpspringboot2.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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

    @OneToOne(mappedBy = "supplier")
    @EqualsAndHashCode.Exclude
    private IncomingDelivery incomingDelivery;

    private String address;
    private int rating;

}

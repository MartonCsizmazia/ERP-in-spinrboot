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
public class Lineitem {

    @Id
    @GeneratedValue
    private Long id;

    private int quantity;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Product product;

    private boolean mergedToStock = false;

    private long fakeDeliveryKey;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    //@ManyToOne
    private IncomingDelivery incomingDelivery;

    private Long IDofIncomingDelivery;

    private Long IDofUserOrder;


/*
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private UserOrder userOrder;
 */

    private Long idOfPackage;

}

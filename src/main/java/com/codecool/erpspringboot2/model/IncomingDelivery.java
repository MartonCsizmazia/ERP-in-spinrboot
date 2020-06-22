package com.codecool.erpspringboot2.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class IncomingDelivery {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Supplier supplier;

    private String date;
    private long fakePrimaryKey;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    private Expense incomingDeliveryExpense;

    @Singular
    @EqualsAndHashCode.Exclude
    //@OneToMany(mappedBy = "incomingDelivery",cascade = {CascadeType.REMOVE})
    //Ha a kommentet csinálom, akkor az adatbázis nem csinál kapcsolótáblát (INCOMING_DELIVERY_STOCK_LINEITEMS) és a
    //incoming delivery JSON lekérdezéskor üres

    @OneToMany(cascade = {CascadeType.ALL})
    //Ha az e fölötti sorban a CASCADETYPE nem ALL, akkor:
    //object references an unsaved transient instance - save the transient instance before flushing” error
    //@OneToMany(cascade = {CascadeType.REMOVE})
    private List<Lineitem> incomingLineitems;


}
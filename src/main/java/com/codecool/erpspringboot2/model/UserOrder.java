package com.codecool.erpspringboot2.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserOrder {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;

    @ManyToOne
    private Customer customer;
    private Status status;
    private boolean hasBeenPaid = false;

    @Singular
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Lineitem> outgoingLineitems;

    private static List<UserOrder> listOfOrders = new ArrayList<>();

    private void addOrderToListOfOrders (UserOrder order){
        listOfOrders.add(order);
    }

}

package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.*;
import com.codecool.erpspringboot2.service.IdCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AllRepositoryTest {

    @Autowired
    private IncomingDeliveryRepository incomingDeliveryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LineitemRepository lineitemRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void saveOneIncomingDelivery(){

        Product doomEternal = Product.builder()
                .manufacturer("EA")
                .name("Doom Eternal")
                .price(5000)
                .profit(1.1)
                .build();
        Product doom2016 = Product.builder()
                .manufacturer("EA")
                .name("Doom 2016")
                .price(3000)
                .profit(1.14)
                .build();
        Product modernWarfare = Product.builder()
                .manufacturer("Activison")
                .name("Modern Warfare")
                .price(4000)
                .profit(1.15)
                .build();
        productRepository.save(doomEternal);
        productRepository.save(doom2016);
        productRepository.save(modernWarfare);

        IdCreator.fakeDeliveryNumber += 1;
        Lineitem lineitem1 = Lineitem.builder()
                .fakeDeliveryKey(IdCreator.fakeDeliveryNumber)
                .product(doom2016)
                .quantity(20)
                .build();
        Lineitem lineitem2 = Lineitem.builder()
                .fakeDeliveryKey(IdCreator.fakeDeliveryNumber)
                .product(doomEternal)
                .quantity(20)
                .build();
        Lineitem lineitem3 = Lineitem.builder()
                .fakeDeliveryKey(IdCreator.fakeDeliveryNumber)
                .product(modernWarfare)
                .quantity(20)
                .build();

        int price = lineitem1.getProduct().getPrice()*lineitem1.getQuantity()+
                lineitem2.getProduct().getPrice()*lineitem2.getQuantity()+
                lineitem3.getProduct().getPrice()*lineitem3.getQuantity();
        Expense expense = Expense.builder()
                .name("First delivery")
                .paid(false)
                .value(price)
                .build();

        expenseRepository.save(expense);

        IncomingDelivery incomingDelivery = IncomingDelivery.builder()
                .fakePrimaryKey(IdCreator.fakeDeliveryNumber)
                .incomingDeliveryExpense(expense)
                .incomingLineitem(lineitem1)
                .incomingLineitem(lineitem2)
                .incomingLineitem(lineitem3)
                .status(Status.ENROUTE)
                .build();



        incomingDeliveryRepository.save(incomingDelivery);

        List<IncomingDelivery> incomingDeliveryList = incomingDeliveryRepository.findAll();
        assertThat(incomingDeliveryList).hasSize(1);
    }

    @Test
    public void saveUniqueCustomerEmailTwice(){

        Customer barbara = Customer.builder()
                .name("Barbara")
                .address("Nagymezo street 44")
                .birthDate(LocalDate.of(1992,10,1))
                .email("lala@lala.hu")
                .dateOfRegistration(LocalDate.now())
                .phoneNumber("063043563")
                .build();

        Customer john = Customer.builder()
                .name("John")
                .address("Nagymezo street 44")
                .birthDate(LocalDate.of(1991,10,1))
                .email("lala@lala.hu")
                .dateOfRegistration(LocalDate.now())
                .phoneNumber("06335555")
                .build();

        customerRepository.save(barbara);
        customerRepository.save(john);
    }


}
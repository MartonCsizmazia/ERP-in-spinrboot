package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.Lineitem;
import com.codecool.erpspringboot2.model.Product;
import com.codecool.erpspringboot2.service.IdCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AllRepositoryTest {

    @Autowired
    private IncomingDeliveryRepository incomingDeliveryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

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


    }

}
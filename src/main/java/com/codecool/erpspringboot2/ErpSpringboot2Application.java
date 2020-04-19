package com.codecool.erpspringboot2;

import com.codecool.erpspringboot2.model.*;
import com.codecool.erpspringboot2.repository.*;
import com.codecool.erpspringboot2.service.IdCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;


@SpringBootApplication
public class ErpSpringboot2Application {

    public static void main(String[] args) {
        SpringApplication.run(ErpSpringboot2Application.class, args);
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private IncomingDeliveryRepository incomingDeliveryRepository;

    @Autowired
    private LineitemRepository lineitemRepository;

    @Autowired
    private InventoryRepository inventoryRepository;



    @Bean
    @Profile("production")
    public CommandLineRunner init (){
        return args -> {
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
                    .email("lallesdrtfhza@lala.hu")
                    .dateOfRegistration(LocalDate.now())
                    .phoneNumber("06335555")
                    .build();
            Customer jane = Customer.builder()
                    .name("Jane")
                    .address("Nagymezo street 44")
                    .birthDate(LocalDate.of(1993,10,1))
                    .email("lalaeqqwrfsdrtl@lala.hu")
                    .dateOfRegistration(LocalDate.now())
                    .phoneNumber("06304356145")
                    .build();

            //if in comment, entites are not saved
            customerRepository.save(barbara);
            customerRepository.save(john);
            customerRepository.save(jane);

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
/*
            lineitemRepository.save(lineitem1);
            lineitemRepository.save(lineitem2);
            lineitemRepository.save(lineitem3);
 */
            IncomingDelivery incomingDelivery1 = IncomingDelivery.builder()
                    .fakePrimaryKey(IdCreator.fakeDeliveryNumber)
                    .incomingLineitem(lineitem1)
                    .incomingLineitem(lineitem2)
                    .incomingLineitem(lineitem3)
                    .status(Status.ENROUTE)
                    .build();

            lineitem1.setIDofIncomingDelivery(incomingDelivery1.getId());
            lineitem2.setIDofIncomingDelivery(incomingDelivery1.getId());
            lineitem3.setIDofIncomingDelivery(incomingDelivery1.getId());

            incomingDeliveryRepository.save(incomingDelivery1);

            cargoPrinter(incomingDelivery1);
            IdCreator.fakeDeliveryNumber += 1;
            Lineitem lineitem4 = Lineitem.builder()
                    .fakeDeliveryKey(IdCreator.fakeDeliveryNumber)
                    .product(doom2016)
                    .quantity(40)
                    .build();
            Lineitem lineitem5 = Lineitem.builder()
                    .fakeDeliveryKey(IdCreator.fakeDeliveryNumber)
                    .product(doomEternal)
                    .quantity(40)
                    .build();
            Lineitem lineitem6 = Lineitem.builder()
                    .fakeDeliveryKey(IdCreator.fakeDeliveryNumber)
                    .product(modernWarfare)
                    .quantity(40)
                    .build();

            lineitemRepository.save(lineitem4);
            lineitemRepository.save(lineitem5);
            lineitemRepository.save(lineitem6);

            Inventory inventory = Inventory.builder()
                    .fakePrimaryKey(IdCreator.fakeDeliveryNumber)
                    .stockLineitem(lineitem4)
                    .stockLineitem(lineitem5)
                    .stockLineitem(lineitem6)

                    .build();

            inventoryRepository.save(inventory);

            cargoPrinter(inventory);
        };
    }

    private void cargoPrinter(Object object){
        if (object.getClass().equals(IncomingDelivery.class)) {
            for (Lineitem incomingLineitem : ((IncomingDelivery) object).getIncomingLineitems()) {
                System.out.println("Product: " + incomingLineitem.getProduct().getName() + "    Quantity: "+ incomingLineitem.getQuantity()+"\n");
            }

        } else if (object.getClass().equals(UserOrder.class)){
            for (Lineitem outgoingLineitem : ((UserOrder) object).getOutgoingLineitems()) {
                System.out.println("Product: " + outgoingLineitem.getProduct().getName() + "    Quantity: "+ outgoingLineitem.getQuantity()+"\n");
            }
        } else if (object.getClass().equals(Inventory.class)) {
            System.out.println("INVENTORY"+ "\n");
            for (Lineitem stockLineitem : ((Inventory) object).getStockLineitems()) {
                System.out.println("Product: " + stockLineitem.getProduct().getName() + "    Quantity: " + stockLineitem.getQuantity() + "\n");
            }
            System.out.println("/////////////////////////////////////");
        }
    }

    public static < E > void printArray( E[] inputArray ) {
        // Display array elements
        for(E element : inputArray) {
            System.out.printf("%s ", element);
        }
        System.out.println();
    }

}

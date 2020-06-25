package com.codecool.erpspringboot2.repository;

import com.codecool.erpspringboot2.model.*;
import com.codecool.erpspringboot2.service.IdCreator;
import com.codecool.erpspringboot2.service.SupplierService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserOrderRepository userOrderRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

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

    @Test(expected = DataIntegrityViolationException.class)
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
        customerRepository.saveAndFlush(john);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void customerEmailShouldBeNotNull(){
        Customer john = Customer.builder()
                .name("John")
                .address("Nagymezo street 44")
                .build();

        customerRepository.saveAndFlush(john);
    }

    @Test(expected = NullPointerException.class)
    public void customerNameShouldBeNotNull(){
        Customer john = Customer.builder()
                .email("lala@lala.hu")
                .address("Nagymezo street 44")
                .build();

        customerRepository.saveAndFlush(john);
    }

    @Test(expected = NullPointerException.class)
    public void employeeNameShouldBeNotNull(){
        Employee porta = Employee.builder()
                .address("Nagymezo street 44")
                .birthDate(LocalDate.of(1983,10,1))
                .email("sfadgdhg@lala.hu")
                .dateOfEmployment(LocalDate.now())
                .phoneNumber("063055474543")
                .salary(400000)
                .build();

        employeeRepository.save(porta);
    }



    @Test
    public void productIsPersistedWithLineitem() {

        Product doom2016 = Product.builder()
                .manufacturer("EA")
                .name("Doom 2016")
                .price(3000)
                .profit(1.14)
                .build();

        Lineitem lineitem1 = Lineitem.builder()
                .fakeDeliveryKey(IdCreator.fakeDeliveryNumber)
                .product(doom2016)
                .quantity(20)
                .build();

        lineitemRepository.save(lineitem1);

        List<Product> products = productRepository.findAll();
        assertThat(products)
                .hasSize(1)
                .allMatch(supplier1 -> supplier1.getId() > 0L);
    }

    @Test
    public void lineitemIsPersistedWithIncomingDelivery(){

        Product doom2016 = Product.builder()
                .manufacturer("EA")
                .name("Doom 2016")
                .price(3000)
                .profit(1.14)
                .build();

        Lineitem lineitem1 = Lineitem.builder()
                .fakeDeliveryKey(IdCreator.fakeDeliveryNumber)
                .product(doom2016)
                .quantity(20)
                .build();

        IncomingDelivery incomingDelivery = IncomingDelivery.builder()
                .fakePrimaryKey(IdCreator.fakeDeliveryNumber)
                .incomingLineitem(lineitem1)
                .status(Status.ENROUTE)
                .build();

        incomingDeliveryRepository.save(incomingDelivery);

        List<Lineitem> lineitems = lineitemRepository.findAll();
        assertThat(lineitems)
                .hasSize(1)
                .allMatch(supplier1 -> supplier1.getId() > 0L);

    }

    @Test
    public void lineitemIsPersistedWithUserOrder(){

        Product doom2016 = Product.builder()
                .manufacturer("EA")
                .name("Doom 2016")
                .price(3000)
                .profit(1.14)
                .build();

        Lineitem lineitem1 = Lineitem.builder()
                .fakeDeliveryKey(IdCreator.fakeDeliveryNumber)
                .product(doom2016)
                .quantity(20)
                .build();

        UserOrder userOrder = UserOrder.builder()
                .outgoingLineitem(lineitem1)
                .build();

        userOrderRepository.save(userOrder);

        List<Lineitem> lineitems = lineitemRepository.findAll();
        assertThat(lineitems)
                .hasSize(1)
                .allMatch(supplier1 -> supplier1.getId() > 0L);
    }

    @Test
    public void saveCustomer(){

        Customer barbara = Customer.builder()
                .name("Barbara")
                .address("Nagymezo street 44")
                .birthDate(LocalDate.of(1992,10,1))
                .email("lala@lala.hu")
                .dateOfRegistration(LocalDate.now())
                .phoneNumber("063043563")
                .build();

        customerRepository.save(barbara);

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(1);

    }

    @Test
    public void saveEmployee(){

        Employee porta = Employee.builder()
                .name("portás Józsi")
                .address("Nagymezo street 44")
                .birthDate(LocalDate.of(1983,10,1))
                .email("sfadgdhg@lala.hu")
                .dateOfEmployment(LocalDate.now())
                .phoneNumber("063055474543")
                .salary(400000)
                .build();

        employeeRepository.save(porta);



        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(1);

    }

    @Test
    public void saveProduct(){

        Product doomEternal = Product.builder()
                .manufacturer("EA")
                .name("Doom Eternal")
                .price(5000)
                .profit(1.1)
                .build();

        productRepository.save(doomEternal);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);

    }

    @Test
    public void customerIdIsGeneratedValue(){

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
                .email("hahaha@lala.hu")
                .dateOfRegistration(LocalDate.now())
                .phoneNumber("06335555")
                .build();

        customerRepository.save(barbara);
        customerRepository.saveAndFlush(john);

        List<Customer> customers = customerRepository.findAll();
        assertNotEquals(customers.get(0).getId(), customers.get(1).getId());

    }




}
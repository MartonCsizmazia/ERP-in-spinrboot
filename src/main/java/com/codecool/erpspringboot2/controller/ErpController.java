package com.codecool.erpspringboot2.controller;

import com.codecool.erpspringboot2.model.Expense;
import com.codecool.erpspringboot2.model.IncomingDelivery;
import com.codecool.erpspringboot2.model.Product;
import com.codecool.erpspringboot2.model.Stock;
import com.codecool.erpspringboot2.service.ExpenseService;
import com.codecool.erpspringboot2.service.IncomingDeliveryService;
import com.codecool.erpspringboot2.service.ProductService;
import com.codecool.erpspringboot2.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Slf4j
public class ErpController {

    @Autowired
    private IncomingDeliveryService incomingDeliveryService;

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/incoming-deliveries")
    public List<IncomingDelivery> displayAllIncomingDelivery(){
        return incomingDeliveryService.getAllIncomingDelivery();
    }

    @GetMapping("/products")
    public List<Product> displayAllProducts(){
        return productService.getAllProduct();
    }

    @GetMapping("/stock")
    public Stock displayStock(){
        return stockService.getStock();
    }

    @GetMapping("/incoming-delivery/uncompleted")
    public List<IncomingDelivery> displayUncompletedIncomingDelivery(){
        return incomingDeliveryService.getAllUncompletedIncomingDelivery();
    }

    @GetMapping("/expenses")
    public List<Expense> displayAllExepenses(){
        return expenseService.getAllExpenses();
    }

    @PostMapping("/incoming-delivery/add")
    public IncomingDelivery addIncomingDelivery(@RequestBody IncomingDelivery incomingDelivery) throws Exception {
        this.incomingDeliveryService.addIncomingDelivery(incomingDelivery);
        return incomingDelivery;
    }

    @PostMapping("/stock/add/{id}")
    public void addToStock(@PathVariable("id") Long id) throws Exception {
        IncomingDelivery incomingDelivery = this.incomingDeliveryService.getIncomingDeliveryById(id);
        incomingDeliveryService.incomingCompleted(incomingDelivery);
    }
}
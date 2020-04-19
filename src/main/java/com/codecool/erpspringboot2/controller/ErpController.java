package com.codecool.erpspringboot2.controller;

import com.codecool.erpspringboot2.model.IncomingDelivery;
import com.codecool.erpspringboot2.model.Inventory;
import com.codecool.erpspringboot2.service.IncomingDeliveryService;
import com.codecool.erpspringboot2.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@Slf4j
public class ErpController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private IncomingDeliveryService incomingDeliveryService;

    @GetMapping("/inventory")
    public Inventory displayInventory(){
        return inventoryService.getInvetory();
    }

    @GetMapping("/incoming-delivery")
    public List<IncomingDelivery> displayAllIncomingDelivery(){
        return incomingDeliveryService.getAllIncomingDelivery();
    }

    @GetMapping("/incoming-delivery/uncompleted")
    public List<IncomingDelivery> displayUncompletedIncomingDelivery(){
        return incomingDeliveryService.getAllUncompletedIncomingDelivery();
    }

    @PostMapping("/incoming-delivery/add")
    public IncomingDelivery addIncomingDelivery(@RequestBody IncomingDelivery incomingDelivery){
        this.incomingDeliveryService.addIncomingDelivery(incomingDelivery);
        return incomingDelivery;
    }

    @PostMapping("/inventory/add/{id}")
    public Inventory addToInventory(@PathVariable("id") Long id) throws Exception {
        IncomingDelivery incomingDelivery = this.incomingDeliveryService.getIncomingDeliveryById(id);
        incomingDeliveryService.incomingCompleted(inventoryService.getInvetory(), incomingDelivery);

        return inventoryService.getInvetory();
    }
}
package com.codecool.erpspringboot2.service;

import com.codecool.erpspringboot2.model.IncomingDelivery;
import com.codecool.erpspringboot2.model.Inventory;
import com.codecool.erpspringboot2.model.Lineitem;
import com.codecool.erpspringboot2.model.Status;
import com.codecool.erpspringboot2.repository.IncomingDeliveryRepository;
import com.codecool.erpspringboot2.repository.InventoryRepository;
import com.codecool.erpspringboot2.repository.LineitemRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class IncomingDeliveryService {

    @Autowired
    private IncomingDeliveryRepository incomingDeliveryRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private LineitemRepository lineitemRepository;

    public List<IncomingDelivery> getAllIncomingDelivery(){
        return incomingDeliveryRepository.findAll();
    }

    public List<IncomingDelivery> getAllUncompletedIncomingDelivery(){
        return incomingDeliveryRepository.findAllByStatusNotLike(Status.COMPLETED);
    }

    public IncomingDelivery getIncomingDeliveryById(Long id){
        return incomingDeliveryRepository.findAllById(id).get(0);
    }

    public void addToInventory(Inventory inventory, IncomingDelivery incomingDelivery){
        for (Lineitem incomingLineitem : incomingDelivery.getIncomingLineitems()) {
            System.out.println(incomingLineitem.getProduct().getName());
            System.out.println(incomingLineitem.getQuantity());
            inventory.getStockLineitems().add(incomingLineitem);
        }
        System.out.println(incomingDelivery.getIncomingLineitems().size());
        inventoryRepository.save(inventory);
    }

    public void incomingCompleted(Inventory inventory, IncomingDelivery incomingDelivery) throws Exception {
        if(incomingDelivery.getStatus()==Status.PROCESSING){
            throw new Exception("This delivery is already added");
        } else {
            addToInventory(inventory, incomingDelivery);
            incomingDelivery.setStatus(Status.COMPLETED);
            incomingDeliveryRepository.save(incomingDelivery);
        }

    }


    public IncomingDelivery addIncomingDelivery(IncomingDelivery paramIncomingDelivery){
        IdCreator.fakeDeliveryNumber += 1;
        List<Lineitem> paramincomingLineitems = paramIncomingDelivery.getIncomingLineitems();
        List<Lineitem> incomingLineitems = new ArrayList<>();
        for (Lineitem incomingLineitem : paramincomingLineitems) {
            Lineitem lineitem = Lineitem.builder()
                    .fakeDeliveryKey(IdCreator.fakeDeliveryNumber)
                    .product(incomingLineitem.getProduct())
                    .quantity(incomingLineitem.getQuantity())
                    .build();
            incomingLineitems.add(lineitem);
            //this.lineitemRepository.save(lineitem);
            //NE SAVELD, KÜLÖNBEN
            //PersistentObjectException: detached entity passed to persist
        }

        IncomingDelivery incomingDelivery = IncomingDelivery.builder()
                .fakePrimaryKey(IdCreator.fakeDeliveryNumber)
                .status(paramIncomingDelivery.getStatus())
                .incomingLineitems(incomingLineitems)
                .build();

        /*NOT WORKING(stack owerlow)
        for (Lineitem incomingLineitem : incomingDelivery.getIncomingLineitems()) {
            incomingLineitem.setIncomingDelivery(incomingDelivery);
        }
         */


        this.incomingDeliveryRepository.save(incomingDelivery);
        System.out.println("THIS IS AND ID "+incomingDelivery.getId());

        for (Lineitem incomingLineitem : lineitemRepository.getAllByFakeDeliveryKey(incomingDelivery.getFakePrimaryKey())) {
            incomingLineitem.setIDofIncomingDelivery(incomingDelivery.getId());
            //incomingLineitem.setIncomingDelivery(incomingDelivery); //NOT WORKING(stack owerlow)
            lineitemRepository.save(incomingLineitem);
        }

        return incomingDelivery;
    }
}

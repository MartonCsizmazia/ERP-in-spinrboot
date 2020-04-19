package com.codecool.erpspringboot2.service;

import com.codecool.erpspringboot2.model.IncomingDelivery;
import com.codecool.erpspringboot2.model.Lineitem;
import com.codecool.erpspringboot2.model.Status;
import com.codecool.erpspringboot2.repository.IncomingDeliveryRepository;
import com.codecool.erpspringboot2.repository.LineitemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IncomingDeliveryService {

    @Autowired
    private IncomingDeliveryRepository incomingDeliveryRepository;

    public List<IncomingDelivery> getAllIncomingDelivery(){
        return incomingDeliveryRepository.findAll();
    }

    public List<IncomingDelivery> getAllUncompletedIncomingDelivery(){
        return incomingDeliveryRepository.findAllByStatusNotLike(Status.COMPLETED);
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

        /*NOT WORKING
        for (Lineitem incomingLineitem : incomingDelivery.getIncomingLineitems()) {
            incomingLineitem.setIncomingDelivery(incomingDelivery);
        }
         */

        this.incomingDeliveryRepository.save(incomingDelivery);
        System.out.println("THIS IS AND ID "+incomingDelivery.getId());


        for (Lineitem incomingLineitem : incomingDelivery.getIncomingLineitems()) {
            incomingLineitem.setIncomingDelivery(incomingDelivery);
        }

        return incomingDelivery;
    }
}

package com.codecool.erpspringboot2.service;

import com.codecool.erpspringboot2.model.*;
import com.codecool.erpspringboot2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class IncomingDeliveryService {

    @Autowired
    private IncomingDeliveryRepository incomingDeliveryRepository;

    @Autowired
    private LineitemRepository lineitemRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<IncomingDelivery> getAllIncomingDelivery(){
        return incomingDeliveryRepository.findAll();
    }

    public List<IncomingDelivery> getAllUncompletedIncomingDelivery(){
        return incomingDeliveryRepository.findAllByStatusNotLike(Status.COMPLETED);
    }

    public IncomingDelivery getIncomingDeliveryById(Long id){
        return incomingDeliveryRepository.findAllById(id).get(0);
    }

    public void chekcForExistingProduct(List<Lineitem> lineitemList, Lineitem lineitem) {
        for (Lineitem newStockLineitem : lineitemList) {
            if (newStockLineitem.getProduct().getId().equals(lineitem.getProduct().getId())) {
                int sum = newStockLineitem.getQuantity();
                sum += lineitem.getQuantity();
                lineitem.setMergedToStock(true);
                newStockLineitem.setQuantity(sum);
                break;
            }
        }
    }

    public void mergeToStockRepository(IncomingDelivery incomingDelivery) {
        Stock stock = stockService.getStock();
        List<Lineitem> newStockLineitems = new ArrayList<>();
        List<Long> idOfNewStockProducts = new ArrayList<>();
        List<Long> idOfStockProducts = new ArrayList<>();

        for (Lineitem stockLineitem : stock.getStockLineitems()) {
            idOfStockProducts.add(stockLineitem.getProduct().getId());
        }

        for (Lineitem incomingLineitem : incomingDelivery.getIncomingLineitems()) {
            if(idOfNewStockProducts.contains(incomingLineitem.getProduct().getId())){
                chekcForExistingProduct(newStockLineitems, incomingLineitem);
            }
            else if(idOfStockProducts.contains(incomingLineitem.getProduct().getId())){
                chekcForExistingProduct(stock.getStockLineitems(), incomingLineitem);
            } else {
                System.out.println("BUILDING");
                Lineitem lineitem = Lineitem.builder()
                        .product(incomingLineitem.getProduct())
                        .quantity(incomingLineitem.getQuantity())
                        .mergedToStock(true)
                        .build();
                newStockLineitems.add(lineitem);
                idOfNewStockProducts.add(lineitem.getProduct().getId());
            }
        }
        System.out.println("ADDING");
        stock.getStockLineitems().addAll(newStockLineitems);
        stockRepository.save(stock);
    }

    public void incomingCompleted(IncomingDelivery incomingDelivery) throws Exception {
        if(incomingDelivery.getStatus()==Status.COMPLETED){
            throw new Exception("This delivery is already added");
        } else {
            mergeToStockRepository(incomingDelivery);
            incomingDelivery.setStatus(Status.COMPLETED);
            for (Lineitem incomingLineitem : incomingDelivery.getIncomingLineitems()) {
                incomingLineitem.setMergedToStock(true);
                lineitemRepository.save(incomingLineitem);
            }
            incomingDeliveryRepository.save(incomingDelivery);
        }
    }

    public void addIncomingDelivery(IncomingDelivery paramIncomingDelivery) throws Exception {
        IdCreator.fakeDeliveryNumber += 1;
        List<Lineitem> paramincomingLineitems = paramIncomingDelivery.getIncomingLineitems();
        List<Lineitem> incomingLineitems = new ArrayList<>();

        List<Product> productList = productRepository.findAll();
        List<String> nameList = new ArrayList<>();

        for (Product product : productList) {

            nameList.add(product.getName());
        }
        System.out.println(nameList);
        System.out.println(productList);
        for (Lineitem incomingLineitem : paramincomingLineitems) {
            if(productList.contains(incomingLineitem.getProduct())) {

                Lineitem lineitem = Lineitem.builder()
                        .fakeDeliveryKey(IdCreator.fakeDeliveryNumber)
                        .product(incomingLineitem.getProduct())
                        .quantity(incomingLineitem.getQuantity())
                        .build();
                incomingLineitems.add(lineitem);
                //this.lineitemRepository.save(lineitem);
                //NE SAVELD, KÜLÖNBEN
                //PersistentObjectException: detached entity passed to persist
            } else {

                if(nameList.contains(incomingLineitem.getProduct().getName())){
                    throw new Exception("There is already a product with this name");
                }
                Product product = Product.builder()
                        .name(incomingLineitem.getProduct().getName())
                        .price(incomingLineitem.getProduct().getPrice())
                        .profit(incomingLineitem.getProduct().getProfit())
                        .manufacturer(incomingLineitem.getProduct().getManufacturer())
                        .build();
                productRepository.save(product);
                Lineitem lineitem = Lineitem.builder()
                        .fakeDeliveryKey(IdCreator.fakeDeliveryNumber)
                        .product(product)
                        .quantity(incomingLineitem.getQuantity())
                        .build();
                incomingLineitems.add(lineitem);
            }
        }
        int price = 0;
        for (Lineitem incomingLineitem : incomingLineitems) {
            price += incomingLineitem.getProduct().getPrice()*incomingLineitem.getQuantity();
        }

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        String date = formatter.format(new Date(System.currentTimeMillis()));



        Expense expense = Expense.builder()
                .name(date + " delivery")
                .value(price)
                .paid(false)
                .date(date)
                .build();
        expenseRepository.save(expense);

        IncomingDelivery incomingDelivery = IncomingDelivery.builder()
                .fakePrimaryKey(IdCreator.fakeDeliveryNumber)
                .incomingDeliveryExpense(expense)
                .status(paramIncomingDelivery.getStatus())
                .incomingLineitems(incomingLineitems)
                .date(date)
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
    }
}

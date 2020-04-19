package com.codecool.erpspringboot2.model;

public enum Status {

    CREATING,   //the delivery is created by me, i give it to the supplier, and the supplier will process it
    PROCESSING,
    ENROUTE,
    COMPLETED //when a delivery is completed, it gets a status of completed, and it add of its content to the invenotry

}

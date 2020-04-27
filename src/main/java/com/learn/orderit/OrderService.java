package com.learn.orderit;

import org.springframework.stereotype.Component;

@Component
public class OrderService {

    public String getOrder(){
        return "New Order is created";
    }
}

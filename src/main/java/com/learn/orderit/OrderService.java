package com.learn.orderit;

import org.springframework.stereotype.Component;

@Component
public class OrderService {

    public String createOrder(){
        return "New Order created";
    }
}

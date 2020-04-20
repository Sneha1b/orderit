package com.learn.orderit;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Controller
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping("/order")
@Api(description = "Order creation API for orderit",
        produces = MediaType.APPLICATION_JSON)
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GET
    @RequestMapping("/create")
    @ResponseBody
    public String createOrder(){
       return orderService.createOrder();
    }
}

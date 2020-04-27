package com.learn.orderit.controllers;

import com.learn.orderit.entity.User;
import com.learn.orderit.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Controller
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestMapping("/user-management")
@Api(description = "User management API for orderit")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @POST
    @PostMapping("/users")
    @ResponseBody
    public String createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GET
    @GetMapping("/users")
    @ResponseBody
    public String getUsers(){
        return userService.getallUsers();
    }

    @GET
    @GetMapping("/users/{userId}")
    @ResponseBody
    public String getUser(@PathVariable Integer userId){
        return userService.getUser(userId);
    }
}

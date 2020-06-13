package com.learn.orderit.controllers;

import com.learn.orderit.models.UserRequest;
import com.learn.orderit.models.UserResponse;
import com.learn.orderit.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Controller
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestMapping("/user-management/users")
@Api(description = "User management API for orderit")
public class UserController {

    private final UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserRequest userRequest){
        log.info("Received request to create user with details {}", userRequest);
        if(!userRequest.validate()){
            UserResponse userResponse = new UserResponse();
            userResponse.setMessage("Bad request to create user");
            return userResponse;
        }
        return userService.createUser(userRequest);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserResponse> getUsers(){
        log.info("Received request to fetch all users");
        return userService.getAllUsers();
    }


    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponse getUser(@PathVariable int userId){
        log.info("Received request to fetch user with ID {}", userId);
        return userService.getUser(userId);
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponse deleteUser(@PathVariable int userId){
        log.info("Received request to delete user with ID {}", userId);
        return userService.deleteUser(userId);
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponse updateUser(@PathVariable int userId, @RequestBody UserRequest userRequest){
        log.info("Received request to update user details with user ID {} and details {}", userId, userRequest);
        return userService.updateUser(userId, userRequest);
    }
}

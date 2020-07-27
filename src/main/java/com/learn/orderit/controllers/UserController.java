package com.learn.orderit.controllers;

import com.learn.orderit.actions.*;
import com.learn.orderit.entity.User;
import com.learn.orderit.models.UserRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URI;

@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestMapping("/users")
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    CreateUserAction createUserAction;
    GetUserAction getUserAction;
    GetAllUsersAction getAllUsersAction;
    DeleteUserAction deleteUserAction;
    UpdateUserAction updateUserAction;
    static Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRequest userRequest){
        String userId = createUserAction.invoke(userRequest);
        URI location = URI.create("/users");
        return ResponseEntity.created(location).header("user_id",userId).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Page<User> getUsers(@PageableDefault(value = 5, page = 0) Pageable pageable){
        log.info("Received request to fetch all users");
        Page userPage = getAllUsersAction.invoke(pageable);
        return userPage;
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public User getUser(@PathVariable String userId){
        log.info("Received request to fetch user with ID {}", userId);
        return getUserAction.invoke(userId);
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId){
        log.info("Received request to delete user with ID {}", userId);
        deleteUserAction.invoke(userId);
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable String userId, @RequestBody @Valid UserRequest userRequest){
        log.info("Received request to update user details with user ID {}", userId);
        updateUserAction.invoke(userId, userRequest);
    }
}

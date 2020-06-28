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
import java.util.List;

@RestController
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestMapping("/user_management/users")
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
        log.info("Received request to create user with details {}", userRequest);
        User user = createUserAction.invoke(userRequest);
        return ResponseEntity.ok().header("user_id",String.valueOf(user.getId())).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> getUsers(@PageableDefault(value = 5, page = 0) Pageable pageable){
        log.info("Received request to fetch all users");
        Page userPage = getAllUsersAction.invoke(pageable);
        return userPage.getContent();
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public User getUser(@PathVariable String userId){
        log.info("Received request to fetch user with ID {}", userId);
        return getUserAction.invoke(Integer.valueOf(userId));
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId){
        log.info("Received request to delete user with ID {}", userId);
        deleteUserAction.invoke(Integer.valueOf(userId));
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable String userId, @RequestBody UserRequest userRequest){
        log.info("Received request to update user details with user ID {} and details {}", userId, userRequest);
        updateUserAction.invoke(Integer.valueOf(userId), userRequest);
    }
}

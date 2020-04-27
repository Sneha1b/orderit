package com.learn.orderit.service;

import com.learn.orderit.entity.User;
import com.learn.orderit.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final IUserRepository userRepo;

    @Autowired
    public UserService(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public String createUser(User user){
        userRepo.save(user);
        return "User successfully created with details" + user.toString();
    }

    public String getallUsers(){
        return userRepo.findAll().toString();
    }

    public String getUser(Integer userId){
        return userRepo.findById(userId).toString();
    }
}

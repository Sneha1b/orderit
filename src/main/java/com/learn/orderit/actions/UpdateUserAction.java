package com.learn.orderit.actions;

import com.learn.orderit.entity.User;
import com.learn.orderit.models.UserRequest;
import com.learn.orderit.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateUserAction {

    private static final Logger log = LoggerFactory.getLogger(GetUserAction.class);
    private final IUserRepository userRepository;

    public void invoke(int userId, UserRequest userRequest){
        log.info("Attempt to update user details");
        Optional<User> user = userRepository.findById(userId);
        Optional.ofNullable(user).map(givenUser -> {
            User updatedUser = givenUser.get();
            if(userRequest.getFirstName()!= null)
                updatedUser.setFirstName(userRequest.getFirstName());
            if(userRequest.getLastName()!= null)
                updatedUser.setLastName(userRequest.getLastName());
            if(userRequest.getEmailId()!= null)
                updatedUser.setEmailId(userRequest.getEmailId());
            if(userRequest.getPhoneNumber()!= null)
                updatedUser.setPhoneNumber(userRequest.getPhoneNumber());
            userRepository.save(updatedUser);
            log.info("Updated user with id {} and user details {}", userId, userRequest);
            return updatedUser;
        }).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + userId + " user update failed"));
    }
}

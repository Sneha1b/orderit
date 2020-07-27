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

    public void invoke(String userId, UserRequest userRequest){
        log.info("Attempt to update user details");
        Optional<User> user = userRepository.findById(userId);
        Optional.ofNullable(user).map(givenUser -> {
            User updatedUser = givenUser.get();
            if(userRequest.getFirst_name()!= null)
                updatedUser.setFirstName(userRequest.getFirst_name());
            if(userRequest.getLast_name()!= null)
                updatedUser.setLastName(userRequest.getLast_name());
            if(userRequest.getEmail_id()!= null)
                updatedUser.setEmailId(userRequest.getEmail_id());
            if(userRequest.getPhone_number()!= null)
                updatedUser.setPhoneNumber(userRequest.getPhone_number());
            userRepository.save(updatedUser);
            log.info("Updated user with id {} and user details {}", userId, userRequest);
            return updatedUser;
        }).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + userId + " user update failed"));
    }
}

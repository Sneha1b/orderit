package com.learn.orderit.actions;

import com.learn.orderit.entity.User;
import com.learn.orderit.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;



@Component
@RequiredArgsConstructor
public class DeleteUserAction {

    private static final Logger log = LoggerFactory.getLogger(DeleteUserAction.class);
    private final IUserRepository userRepository;

    public void invoke(String userId){
        Optional<User> user = userRepository.findById(userId);
        Optional.ofNullable(user).map(givenUser -> {
            userRepository.delete(givenUser.get());
            log.info("User with ID {}  deleted", userId);
            return givenUser.get();
        }).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + userId + " user delete failed"));
    }
}

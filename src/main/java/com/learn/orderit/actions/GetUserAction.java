package com.learn.orderit.actions;

import com.learn.orderit.entity.User;
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
public class GetUserAction {

    private static final Logger log = LoggerFactory.getLogger(GetUserAction.class);
    private final IUserRepository userRepository;

    public User invoke(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        return Optional.ofNullable(user).map(givenUser -> {
            log.info("User with ID {}  fetched", userId);
            return givenUser.get();
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + userId + " not Found "));
    }
}

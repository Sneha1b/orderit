package com.learn.orderit.actions;

import com.learn.orderit.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllUsersAction {

    private static final Logger log = LoggerFactory.getLogger(GetUserAction.class);
    private final IUserRepository userRepository;

    public Page invoke(Pageable page) {
        log.info("User list fetched");
        Page userList = userRepository.findAll(page);
        return userList;
    }
}

package com.learn.orderit.service;

import com.learn.orderit.entity.User;
import com.learn.orderit.models.UserRequest;
import com.learn.orderit.models.UserResponse;
import com.learn.orderit.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
        modelMapper = new ModelMapper();
        configureModelMapper();
    }

    public UserResponse createUser(UserRequest userRequest) {

        User user = modelMapper.map(userRequest, User.class);
        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(System.currentTimeMillis());
        log.info("Created user with details {}", user);
        userRepository.save(user);


        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        StringBuilder responseMessage = new StringBuilder("Successfully created user ");
        responseMessage.append(user);
        userResponse.setMessage(responseMessage.toString());

        return userResponse;
    }
    public List<UserResponse> getAllUsers() {
        List<UserResponse> userResponseList = userRepository.findAll().stream().map(src -> modelMapper.map(src, UserResponse.class)).collect(Collectors.toList());
        userResponseList.stream().forEach(src -> src.setMessage("Found user with ID " + src.getId()));
        return userResponseList;
    }

    public UserResponse getUser(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        UserResponse userResponse = new UserResponse();
        if (user.isPresent()) {
            userResponse = modelMapper.map(user.get(), UserResponse.class);
            userResponse.setMessage("Found user with ID " + userId);
            log.info("User with ID {}  fetched", userId);
        } else {
            userResponse.setMessage("User with ID not Found " + userId);
            log.info("User with ID {} not found, user fetch failed", userId);
        }
        return userResponse;
    }

    public UserResponse deleteUser(Integer userId){
        Optional<User> user = userRepository.findById(userId);
        UserResponse userResponse = new UserResponse();
        if(user.isPresent()) {
            userRepository.delete(user.get());
            userResponse = modelMapper.map(user.get(), UserResponse.class);
            userResponse.setMessage("Deleted user with ID " + userResponse.getId());
            log.info("User with ID {}  deleted", userId);
        }
        else{
            userResponse.setMessage("User with ID not found " + userId + " user delete failed");
            log.info("User with ID {} not found, user delete failed", userId);

        }

        return userResponse;
    }

    public UserResponse updateUser(int userId, UserRequest userRequest){
        log.info("Attempt to update user details");
        Optional<User> user = userRepository.findById(userId);
        UserResponse userResponse = new UserResponse();
        if(user.isPresent()) {
            userResponse = modelMapper.map(user.get(), UserResponse.class);
            userResponse.setMessage("Updated user with ID " + userResponse.getId());
            userRepository.update(userId, userRequest.getFirstName(), userRequest.getLastName(), new Date(userRequest.getBirthDate()), userRequest.getEmailId(), userRequest.getPhoneNumber());
            log.info("Updated user with id {} and user details {}", userId, userRequest);
        }
        else{
            userResponse.setMessage("User with ID not found " + userId + " user update failed");
            log.info("User with ID {} not found, user update failed", userId);
        }

        return userResponse;
    }

    private void configureModelMapper() {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        configureModelMapperForUser();
        configureModelMapperForResponse();
        log.info("ModelMapper configured");
    }

    private void configureModelMapperForUser() {
        PropertyMap<UserRequest, User> skipFields = new PropertyMap<UserRequest, User>() {
            @Override
            protected void configure() {
                skip().setCreatedAt(null);
                skip().setUpdatedAt(null);
                skip().setId(1);
            }
        };
        modelMapper.typeMap(UserRequest.class, User.class).addMapping(src -> src.getFirstName(), User::setFirstName);
        modelMapper.typeMap(UserRequest.class, User.class).addMapping(src -> src.getLastName(), User::setLastName);
        modelMapper.typeMap(UserRequest.class, User.class).addMapping(src -> src.getPhoneNumber(), User::setPhoneNumber);
        modelMapper.typeMap(UserRequest.class, User.class).addMapping(src -> src.getEmailId(), User::setEmailId);
        modelMapper.typeMap(UserRequest.class, User.class).addMapping(src -> src.getBirthDate(), User::setBirthDate);
        modelMapper.addMappings(skipFields);

    }

    private void configureModelMapperForResponse() {
        modelMapper.typeMap(User.class, UserResponse.class).addMapping(src -> src.getId(), UserResponse::setId);
    }
}

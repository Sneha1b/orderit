package com.learn.orderit.actions;

import com.learn.orderit.entity.User;
import com.learn.orderit.models.UserRequest;
import com.learn.orderit.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserAction {

    private static final Logger log = LoggerFactory.getLogger(CreateUserAction.class);
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper =  new ModelMapper();

    public String invoke(UserRequest userRequest) {
        configureModelMapperForUser();

        User user = modelMapper.map(userRequest, User.class);
        log.info("Created user with details {}", user);
        userRepository.save(user);

        return user.getId();
    }

    private void configureModelMapperForUser() {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        PropertyMap<UserRequest, User> skipFields = new PropertyMap<UserRequest, User>() {
            @Override
            protected void configure() {
                skip().setCreatedAt(null);
                skip().setUpdatedAt(null);
                skip().setId("1");
            }
        };
        modelMapper.typeMap(UserRequest.class, User.class).addMapping(src -> src.getFirst_name(), User::setFirstName);
        modelMapper.typeMap(UserRequest.class, User.class).addMapping(src -> src.getLast_name(), User::setLastName);
        modelMapper.typeMap(UserRequest.class, User.class).addMapping(src -> src.getPhone_number(), User::setPhoneNumber);
        modelMapper.typeMap(UserRequest.class, User.class).addMapping(src -> src.getEmail_id(), User::setEmailId);
        modelMapper.typeMap(UserRequest.class, User.class).addMapping(src -> src.getBirth_date(), User::setBirthDate);
        modelMapper.addMappings(skipFields);

        log.info("ModelMapper configured");
    }
}

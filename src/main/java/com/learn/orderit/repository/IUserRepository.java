package com.learn.orderit.repository;

import com.learn.orderit.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface IUserRepository extends CrudRepository<User, Integer>{
    @Override
    Iterable<User> findAll();

    @Override
    <S extends User> S save(S s);

    @Override
    Optional<User> findById(Integer integer);
}

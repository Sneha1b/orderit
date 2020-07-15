package com.learn.orderit.repository;

import com.learn.orderit.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface IUserRepository extends CrudRepository<User, String>, PagingAndSortingRepository<User, String> {
}

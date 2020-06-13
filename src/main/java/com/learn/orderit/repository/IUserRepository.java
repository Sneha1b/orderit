package com.learn.orderit.repository;

import com.learn.orderit.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public interface IUserRepository extends CrudRepository<User, Integer>{
    @Override
    List<User> findAll();

    @Override
    <S extends User> S save(S s);

    @Override
    Optional<User> findById(Integer integer);

    @Modifying
    @Transactional
    @Query(value = "update users as u set u.first_name = ?2, u.last_name = ?3, u.birth_date = ?4, u.email_id = ?5, u.phone_number = ?6 where u.id = ?1", nativeQuery = true)
    void update(int userId, String firstName, String lastName, Date birthDate, String emailId, String phoneNumber);
}

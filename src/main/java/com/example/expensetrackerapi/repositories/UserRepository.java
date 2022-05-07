package com.example.expensetrackerapi.repositories;

import com.example.expensetrackerapi.domain.User;
import com.example.expensetrackerapi.exceptions.EtAuthException;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository {

    Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;

    User findByEmailAndPassword(String email, String password) throws EtAuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);
}

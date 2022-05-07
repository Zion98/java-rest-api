package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.domain.User;
import com.example.expensetrackerapi.exceptions.EtAuthException;
import com.example.expensetrackerapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        return null;
    }

    @Override
    public User registerUser(String firstName,
                             String lastName,
                             String email,
                             String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        System.out.println("--=============I am here first---------------");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid email format");
        System.out.println("I am here---------------");
        Integer count = userRepository.getCountByEmail(email);

        if(count > 0)
            throw new EtAuthException("Email already in use");

        Integer userId = userRepository.create(firstName, lastName, email, password);
        return userRepository.findById(userId);
    }
}

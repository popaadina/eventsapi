package com.app.eventsmanagementapi.service;

import com.app.eventsmanagementapi.dto.LoginRequest;
import com.app.eventsmanagementapi.dto.RegisterRequest;
import com.app.eventsmanagementapi.models.User;
import com.app.eventsmanagementapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;


    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());

        userRepository.save(user);
    }

    public boolean login(LoginRequest loginRequest) {


        for(User user: userRepository.findAll() ) {
            if ((loginRequest.getPassword().equals(user.getPassword())) &&
                    (loginRequest.getUsername().equals(user.getUsername())) &&
                    (!user.getAdmin())
            )
                return true;
        }
         return false;
    }

    public boolean loginAdmin(LoginRequest loginRequest) {


        for(User user: userRepository.findAll() ) {
            System.out.println(user.getAdmin());
            System.out.println(loginRequest.getPassword().equals(user.getPassword()));
            System.out.println((loginRequest.getUsername().equals(user.getUsername())));
            System.out.println(user.getPassword());
            System.out.println(loginRequest.getPassword());
            if ((loginRequest.getPassword().equals(user.getPassword())) &&
                    (loginRequest.getUsername().equals(user.getUsername())) &&
                    (user.getAdmin())
            )
                return true;
        }
        return false;
    }
}

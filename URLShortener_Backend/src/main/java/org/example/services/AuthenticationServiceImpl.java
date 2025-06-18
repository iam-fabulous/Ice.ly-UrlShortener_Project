package org.example.services;

import lombok.AllArgsConstructor;
import org.example.Exceptions.UserNotFoundException;
import org.example.data.models.UrlShortener;
import org.example.data.models.User;
import org.example.data.repositories.UserRepo;
import org.example.dtos.request.CreateAccountRequest;
import org.example.dtos.request.LoginRequest;
import org.example.dtos.response.CreateAccountResponse;
import org.example.dtos.response.LoginResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service

public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepo userRepo;

    public AuthenticationServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public CreateAccountResponse register(CreateAccountRequest request) {
        checkIfUserExists(request.getUsername());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setLoggedIn(false);
        user.setRegistered(true);
        user.setCreatedAt(LocalDateTime.now());
        userRepo.save(user);
        CreateAccountResponse response = new CreateAccountResponse();
        response.setUserId(user.getUserId());
        response.setMessage("Successfully Registered!");
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        return response;
    }

    private void checkIfUserExists(String username) {
       if(userRepo.findByUsername(username) != null) {
           throw new UserNotFoundException("Username " + username + " already exists!");
       }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = getUser(loginRequest);
        validateUser(loginRequest, user);
        logIn(user);
        LoginResponse response = new LoginResponse();
        response.setLoggedIn(user.isLoggedIn());
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        return response;
    }

    private User getUser(LoginRequest loginRequest) {
        return userRepo.findByUsername(loginRequest.getUsername());
    }

    private static void logIn(User user) {
        assert user != null;
        user.setLoggedIn(true);
        System.out.println("Logged in Successfully!");
    }

    private static void validateUser(LoginRequest loginRequest, User user) {
        if(user == null || !user.getPassword().equals(loginRequest.getPassword()) || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new IllegalArgumentException("Invalid username or password!");
        }
    }

    @Override
    public boolean logout() {
        User user = new User();
        if(userRepo.findByUsername(user.getUsername()) != null || user.isLoggedIn()) {
            userRepo.findByUsername(user.getUsername());
        }
        user.setLoggedIn(false);
        userRepo.save(user);
        return true;
    }
}

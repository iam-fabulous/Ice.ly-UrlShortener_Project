package org.example.services;

import org.example.Exceptions.UserNotFoundException;
import org.example.data.models.User;
import org.example.data.repositories.UserRepo;
import org.example.dtos.request.CreateAccountRequest;
import org.example.dtos.request.LoginRequest;
import org.example.dtos.response.CreateAccountResponse;
import org.springframework.stereotype.Service;

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
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setLoggedIn(false);
        user.setRegistered(true);
        user.setCreatedAt(request.getLocalDateTime());
        userRepo.save(user);
        CreateAccountResponse response = new CreateAccountResponse();
        response.setMessage("Successfully Registered!");

        return response;
    }

    private void checkIfUserExists(String username) {
       if(userRepo.findByUsername(username) != null) {
           throw new UserNotFoundException("Username " + username + " already exists!");
       }
    }

    @Override
    public boolean login(LoginRequest loginRequest) {
        User user = getUser(loginRequest);
        validateUser(loginRequest, user);
        logIn(user);
        return user.isLoggedIn();
    }

    private User getUser(LoginRequest loginRequest) {
        User user = userRepo.findByUsername(loginRequest.getUsername());
        return user;
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

package org.example.services;

import org.example.dtos.request.CreateAccountRequest;
import org.example.dtos.request.LoginRequest;
import org.example.dtos.response.CreateAccountResponse;
import org.example.dtos.response.LoginResponse;

public interface AuthenticationService {
    CreateAccountResponse register(CreateAccountRequest request);
    LoginResponse login(LoginRequest loginRequest);
    boolean logout();
}

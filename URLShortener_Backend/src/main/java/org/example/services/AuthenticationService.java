package org.example.services;

import org.example.dtos.request.CreateAccountRequest;
import org.example.dtos.request.LoginRequest;
import org.example.dtos.response.CreateAccountResponse;

public interface AuthenticationService {
    CreateAccountResponse register(CreateAccountRequest request);
    boolean login(LoginRequest loginRequest);
    boolean logout();
}

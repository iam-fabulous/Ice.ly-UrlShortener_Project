package org.example.controllers;

import org.example.Exceptions.UserNotFoundException;
import org.example.dtos.request.CreateAccountRequest;
import org.example.dtos.request.LoginRequest;
import org.example.dtos.response.AccountApiResponse;
import org.example.dtos.response.CreateAccountResponse;
import org.example.dtos.response.ErrorResponse;
import org.example.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.naming.AuthenticationException;
import java.security.AuthProvider;

public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateAccountRequest createAccountRequest) throws UserNotFoundException {
       try{
           CreateAccountResponse createAccountResponse = authenticationService.register(createAccountRequest);
           return ResponseEntity.status(HttpStatus.CREATED).body(createAccountResponse);
       }
       catch(UserNotFoundException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body(new ErrorResponse("Registration failed", e.getMessage()));
       }
       catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new ErrorResponse("An unexpected error occurred", "Please try again later."));
       }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            boolean loginSuccessful = authenticationService.login(loginRequest);
            return getAccountApiResponseResponseEntity(loginRequest, loginSuccessful);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new AccountApiResponse(false, "An unexpected error occurred", null)
            );
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            boolean logoutSuccessful = authenticationService.logout();

            if (logoutSuccessful) {
                return ResponseEntity.ok(new AccountApiResponse(true, "Logout successful", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AccountApiResponse(false, "Logout failed", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AccountApiResponse(false, "An unexpected error occurred", null));
        }
    }

    private static ResponseEntity<AccountApiResponse> getAccountApiResponseResponseEntity(LoginRequest loginRequest, boolean loginSuccessful) {
        if (loginSuccessful) {
            return ResponseEntity.ok(
                    new AccountApiResponse(true, "Login successful", loginRequest.getUsername())
            );
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new AccountApiResponse(false, "Invalid credentials", null)
            );
        }
    }

}

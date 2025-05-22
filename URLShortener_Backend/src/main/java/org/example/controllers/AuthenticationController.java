package org.example.controllers;

import org.example.Exceptions.RegistrationException;
import org.example.Exceptions.UnexpectedError;
import org.example.Exceptions.UserNotFoundException;
import org.example.dtos.request.CreateAccountRequest;
import org.example.dtos.request.LoginRequest;
import org.example.dtos.response.AccountApiResponse;
import org.example.dtos.response.CreateAccountResponse;
import org.example.dtos.response.ErrorResponse;
import org.example.dtos.response.LoginResponse;
import org.example.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.security.AuthProvider;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {
//    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateAccountRequest createAccountRequest){
       try{
           CreateAccountResponse createAccountResponse = authenticationService.register(createAccountRequest);
//           return ResponseEntity.status(HttpStatus.CREATED).body(createAccountResponse);
           return  ResponseEntity.ok(createAccountResponse);
       }

       catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body(new RegistrationException("Registration failed " + e.getMessage()));
       }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginSuccessful = authenticationService.login(loginRequest);
            return getAccountApiResponseResponseEntity(loginRequest, loginSuccessful);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new UnexpectedError("An unexpected error occurred")
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
                        .body(new UnexpectedError("Login Failed!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UnexpectedError("An unexpected error occurred"));
        }
    }

    private static ResponseEntity<AccountApiResponse> getAccountApiResponseResponseEntity(LoginRequest loginRequest, LoginResponse loginSuccessful) {
        if (loginSuccessful.getIsLoggedIn()) {
            return ResponseEntity.ok(
                    new AccountApiResponse(true, loginRequest.getUsername(), "Login successful")
            );
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new AccountApiResponse(false, "Invalid credentials")
            );
        }
    }

}

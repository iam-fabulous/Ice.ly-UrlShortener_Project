package org.example.services;

import org.example.data.repositories.UserRepo;
import org.example.dtos.request.CreateAccountRequest;
import org.example.dtos.request.LoginRequest;
import org.example.dtos.response.CreateAccountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthenticationServiceImplTest {

    @Autowired
    private AuthenticationService authenticationService;
    CreateAccountRequest createAccountRequest;

    @Autowired
    UserRepo userRepo;
    @BeforeEach
    void setUp() {
        userRepo.deleteAll();

        createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setUsername("Fabulous");
        createAccountRequest.setPassword("123456");
        createAccountRequest.setEmail("test@example.com");
    }

    @Test
    public void testThatUserCanRegister(){
        CreateAccountResponse response = authenticationService.register(createAccountRequest);
        assertNotNull(response);
    }

    @Test
    public void testThatUserCanLogin(){
        CreateAccountResponse response = authenticationService.register(createAccountRequest);
        assertNotNull(response);
        assertEquals("Fabulous", createAccountRequest.getUsername());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Fabulous");
        loginRequest.setPassword("123456");
        boolean loginResponse = authenticationService.login(loginRequest);
        assertTrue(loginResponse);

    }

    @Test
    public void testThatUserCanLogout(){
        CreateAccountResponse response = authenticationService.register(createAccountRequest);
        assertNotNull(response);
        assertEquals("Fabulous", createAccountRequest.getUsername());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Fabulous");
        loginRequest.setPassword("123456");
        boolean loginResponse = authenticationService.login(loginRequest);
        assertTrue(loginResponse);

        boolean logOutResponse = authenticationService.logout();
        assertTrue(logOutResponse);
    }
}
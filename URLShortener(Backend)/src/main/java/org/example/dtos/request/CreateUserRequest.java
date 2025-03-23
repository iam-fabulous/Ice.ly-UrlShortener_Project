package org.example.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CreateUserRequest {
//    private String userId;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
}

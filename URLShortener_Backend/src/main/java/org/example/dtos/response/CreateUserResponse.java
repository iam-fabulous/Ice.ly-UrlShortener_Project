package org.example.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserResponse {
    private String message;
    private String email;
    private String username;
//    private String userId;
}

package org.example.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CreateAccountRequest {
    private String username;
    private String email;
    private String password;
    private LocalDateTime localDateTime;
}

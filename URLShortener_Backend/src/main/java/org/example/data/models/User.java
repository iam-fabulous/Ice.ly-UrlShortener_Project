package org.example.data.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class User {
    @Id
    private String userId;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private boolean isRegistered;
    private boolean isLoggedIn;
}

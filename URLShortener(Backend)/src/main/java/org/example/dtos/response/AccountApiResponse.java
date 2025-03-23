package org.example.dtos.response;

import lombok.*;

@Data
@AllArgsConstructor
public class AccountApiResponse {
    private boolean success;
    private String message;
    private String username;
}

package org.example.dtos.response;

import lombok.*;

@Data
//@AllArgsConstructor
public class AccountApiResponse {
    private boolean success;
    private String message;
    private String username;

    public AccountApiResponse() {

    }
    public AccountApiResponse(boolean success) {
        this.success = success;
    }
    public AccountApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public AccountApiResponse(boolean success, String username, String message) {
        this.success = success;
        this.username = username;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

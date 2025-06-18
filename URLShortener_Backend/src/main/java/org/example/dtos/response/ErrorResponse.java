package org.example.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
//@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String message;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}



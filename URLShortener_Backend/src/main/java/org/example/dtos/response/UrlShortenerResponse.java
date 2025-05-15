package org.example.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UrlShortenerResponse {

    private String originalUrl;
    private String shortUrl;
    private LocalDateTime createdAt;
}

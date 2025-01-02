package org.example.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document

public class UrlShortener {
    @Id
    private String id;
    private User userId;
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime createdAt;
}

package org.example.dtos.request;


import lombok.Getter;
import lombok.Setter;
import org.example.data.models.UrlShortener;
import org.example.data.models.User;

import java.time.LocalDateTime;

//@Getter
//@Setter
public class UrlShortenerRequest {

//    private String urlId;
    private User userId;
    private String originalUrl;
    private String shortUrl;
//    private UrlShortener oldOriginalUrl;
    private LocalDateTime createdAt;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

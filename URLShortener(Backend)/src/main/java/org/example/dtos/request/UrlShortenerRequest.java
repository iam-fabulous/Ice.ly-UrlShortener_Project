package org.example.dtos.request;


import lombok.Getter;
import lombok.Setter;
import org.example.data.models.UrlShortener;

import java.time.LocalDateTime;

@Getter
@Setter
public class UrlShortenerRequest {

    private String urlId;
    private String userId;
    private String originalUrl;
    private String shortUrl;
    private UrlShortener oldOriginalUrl;
    private LocalDateTime createdAt;

}

package org.example.services;

import org.example.dtos.request.UrlShortenerRequest;
import org.example.dtos.response.UrlShortenerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UrlShortenerService {
    UrlShortenerResponse generateShortUrl(UrlShortenerRequest originalUrl);
    UrlShortenerResponse fetchOriginalUrl(UrlShortenerRequest request);
    UrlShortenerResponse changeOriginalUrl(UrlShortenerRequest originalUrl);
    List<UrlShortenerResponse> viewAllUrls();
    boolean  deleteOriginalUrl(UrlShortenerRequest originalUrl);
}

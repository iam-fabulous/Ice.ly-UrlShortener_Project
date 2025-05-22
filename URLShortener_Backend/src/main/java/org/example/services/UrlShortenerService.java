package org.example.services;

import org.example.dtos.request.UrlShortenerRequest;
import org.example.dtos.response.UrlShortenerResponse;

import java.util.List;

public interface UrlShortenerService {
    UrlShortenerResponse generateShortUrl(UrlShortenerRequest originalUrl);
    UrlShortenerResponse fetchOriginalUrl(UrlShortenerRequest request);
    UrlShortenerResponse changeOriginalUrl(UrlShortenerRequest originalUrl);
    List<UrlShortenerResponse> viewAllUrls();
    boolean  deleteOriginalUrl(UrlShortenerRequest originalUrl);
}

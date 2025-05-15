package org.example.data.repositories;

import org.example.data.models.UrlShortener;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortenerRepo extends MongoRepository<UrlShortener, String> {
    UrlShortener findOriginalUrlByShortUrl(String shortUrl);
}

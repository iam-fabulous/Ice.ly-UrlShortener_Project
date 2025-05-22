package org.example.services;

import org.example.data.repositories.UrlShortenerRepo;
import org.example.dtos.request.UrlShortenerRequest;
import org.example.dtos.response.UrlShortenerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UrlShortenerServiceImplTest {

    @Autowired
    private UrlShortenerService urlShortenerService;
    private UrlShortenerRequest urlShortenerRequest;
    private UrlShortenerRequest urlShortenerRequest2;

    @Autowired
    UrlShortenerRepo urlShortenerRepo;

    @BeforeEach
    void setUp() {
        urlShortenerRepo.deleteAll();
        urlShortenerRequest = new UrlShortenerRequest();
        urlShortenerRequest.setOriginalUrl("http://example.com");
        urlShortenerRequest2 = new UrlShortenerRequest();
        urlShortenerRequest2.setOriginalUrl("http://second-example.com");

    }

    @Test
    public void shortenedUrlCanBeGenerated() {
        UrlShortenerResponse urlShortenerResponse = urlShortenerService.generateShortUrl(urlShortenerRequest);
        assertEquals(urlShortenerRequest.getOriginalUrl(), urlShortenerResponse.getOriginalUrl());
        assertNotNull(urlShortenerResponse);
    }

    @Test
    public void UrlShortenerCanFindOriginalUrlUsingShortUrl() {
        UrlShortenerResponse urlShortenerResponse = urlShortenerService.generateShortUrl(urlShortenerRequest);
        assertEquals(urlShortenerRequest.getOriginalUrl(), urlShortenerResponse.getOriginalUrl());
        assertNotNull(urlShortenerResponse);

        UrlShortenerRequest fetchUrlRequest = new UrlShortenerRequest();
        fetchUrlRequest.setShortUrl(urlShortenerResponse.getShortUrl());

        UrlShortenerResponse fetchedResponse = urlShortenerService.fetchOriginalUrl(fetchUrlRequest);

        assertNotNull(fetchedResponse);
        assertEquals(urlShortenerRequest.getOriginalUrl(), fetchedResponse.getOriginalUrl());
        assertEquals("http://example.com", fetchedResponse.getOriginalUrl());

    }

    @Test
    public void UrlShortenerCanChangeOriginalUrl() {
        UrlShortenerResponse urlShortenerResponse = urlShortenerService.generateShortUrl(urlShortenerRequest);
        assertEquals(urlShortenerRequest.getOriginalUrl(), urlShortenerResponse.getOriginalUrl());
        assertNotNull(urlShortenerResponse);

        UrlShortenerRequest updatedRequest = new UrlShortenerRequest();
        updatedRequest.setShortUrl(urlShortenerResponse.getShortUrl());
        updatedRequest.setOriginalUrl("http://newexample.com");

        UrlShortenerResponse updatedResponse = urlShortenerService.changeOriginalUrl(updatedRequest);
        assertNotNull(updatedResponse);
        assertEquals("http://newexample.com", updatedResponse.getOriginalUrl());
        assertEquals( urlShortenerResponse.getShortUrl(), updatedResponse.getShortUrl());
    }

    @Test
    public void UrlShortenerCanDeleteOriginalUrl() {
        UrlShortenerResponse urlShortenerResponse = urlShortenerService.generateShortUrl(urlShortenerRequest);
        assertEquals(urlShortenerRequest.getOriginalUrl(), urlShortenerResponse.getOriginalUrl());
        assertNotNull(urlShortenerResponse);

        UrlShortenerRequest deleteUrlRequest = new UrlShortenerRequest();
        deleteUrlRequest.setShortUrl(urlShortenerResponse.getShortUrl());

        boolean deleteUrlResponse = urlShortenerService.deleteOriginalUrl(deleteUrlRequest);
        assertTrue(deleteUrlResponse);

        assertThrows(IllegalArgumentException.class, () -> {
            urlShortenerService.fetchOriginalUrl(deleteUrlRequest);
        });
    }

    @Test
    public void UrlShortenerCanViewAllUrl() {

        UrlShortenerResponse urlShortenerResponse = urlShortenerService.generateShortUrl(urlShortenerRequest);
        assertEquals(urlShortenerRequest.getOriginalUrl(), urlShortenerResponse.getOriginalUrl());
        assertNotNull(urlShortenerResponse);


        UrlShortenerResponse urlShortenerResponse2 = urlShortenerService.generateShortUrl(urlShortenerRequest2);
        assertEquals(urlShortenerRequest2.getOriginalUrl(), urlShortenerResponse2.getOriginalUrl());
        assertNotNull(urlShortenerResponse2);


        List<UrlShortenerResponse> viewAllResponse = urlShortenerService.viewAllUrls();


        assertNotNull(viewAllResponse);
        assertEquals(2, viewAllResponse.size());


        assertEquals(urlShortenerRequest.getOriginalUrl(), viewAllResponse.get(0).getOriginalUrl());
        assertEquals(urlShortenerRequest2.getOriginalUrl(), viewAllResponse.get(1).getOriginalUrl());


        assertEquals(urlShortenerResponse.getShortUrl(), viewAllResponse.get(0).getShortUrl());
        assertEquals(urlShortenerResponse2.getShortUrl(), viewAllResponse.get(1).getShortUrl());
    }

}
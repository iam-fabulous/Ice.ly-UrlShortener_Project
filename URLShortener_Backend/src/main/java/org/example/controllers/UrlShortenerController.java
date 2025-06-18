package org.example.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Exceptions.UnexpectedError;
import org.example.data.models.UrlShortener;
import org.example.data.repositories.UrlShortenerRepo;
import org.example.dtos.request.UrlShortenerRequest;
import org.example.dtos.response.ErrorResponse;
import org.example.dtos.response.UrlShortenerResponse;
import org.example.services.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;
    private final UrlShortenerRepo urlShortenerRepo;

    public UrlShortenerController(UrlShortenerService urlShortenerService, UrlShortenerRepo urlShortenerRepo) {
        this.urlShortenerService = urlShortenerService;
        this.urlShortenerRepo = urlShortenerRepo;
    }

    @PostMapping("/generateShortUrl")
    public ResponseEntity<?> generateShortUrl(@RequestBody UrlShortenerRequest request) {
        try {
            UrlShortenerResponse response = urlShortenerService.generateShortUrl(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Invalid request", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred", "Please try again later."));
        }
    }

    @PostMapping("/fetchOriginalUrl")
    public ResponseEntity<?> fetchOriginalUrl(@RequestBody UrlShortenerRequest request) {
        try{
            UrlShortenerResponse response = urlShortenerService.fetchOriginalUrl(request);
            return ResponseEntity.ok(response);
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Invalid request", ex.getMessage()));
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred", ex.getMessage()));
        }
    }

    @PostMapping("/changeOriginalUrl")
    public ResponseEntity<?> changeOriginalUrl(@RequestBody UrlShortenerRequest request) {
        try{
            UrlShortenerResponse response = urlShortenerService.changeOriginalUrl(request);
            return ResponseEntity.ok(response);
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Invalid request", ex.getMessage()));
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred", ex.getMessage()));
        }
    }


        @GetMapping("/viewAllUrls")
        public ResponseEntity<?> viewAllUrls() {
            try {
                List<UrlShortenerResponse> responses = urlShortenerService.viewAllUrls();
                if (responses.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT)
                            .body(new UnexpectedError("No URLs found"));
                }
                return ResponseEntity.ok(responses);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse("Internal server error", "Unable to fetch URLs. Please try again later."));
            }
        }


    @PostMapping("/deleteOriginalUrl")
    public ResponseEntity<?> deleteOriginalUrl(@RequestBody UrlShortenerRequest request) {
        try {
            boolean isDeleted = urlShortenerService.deleteOriginalUrl(request);
            if (isDeleted) {
                return ResponseEntity.ok("URL deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Deletion failed", "The specified URL does not exist."));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error", "An unexpected error occurred. Please try again later."));
        }
    }

    @GetMapping("/{shortUrl}")
    @ResponseBody
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortUrl) {
        // Retrieve the original URL from your database or in-memory store
        String originalUrl = findOriginalUrl(shortUrl);

        // If the shortUrl doesn't exist in the database
        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }

        // Redirect to the original URL
        return ResponseEntity.status(HttpStatus.FOUND) // HTTP 302 Redirect status
                .header("Location", originalUrl)
                .build();
    }

    private String findOriginalUrl(String shortUrl) {
        UrlShortener urlShortener = urlShortenerRepo.findByShortUrl(shortUrl);
        if (urlShortener == null) {
            return null;
        }
        return urlShortener.getOriginalUrl();
    }

}

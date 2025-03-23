package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dtos.request.UrlShortenerRequest;
import org.example.dtos.response.ErrorResponse;
import org.example.dtos.response.UrlShortenerApiResponse;
import org.example.dtos.response.UrlShortenerResponse;
import org.example.services.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

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
                            .body(new UrlShortenerApiResponse("No URLs found"));
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
                return ResponseEntity.ok(new UrlShortenerApiResponse("URL deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Deletion failed", "The specified URL does not exist."));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal server error", "An unexpected error occurred. Please try again later."));
        }
    }

}

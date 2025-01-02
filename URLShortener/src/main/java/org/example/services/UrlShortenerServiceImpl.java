package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.data.models.UrlShortener;
import org.example.data.repositories.UrlShortenerRepo;
import org.example.dtos.request.UrlShortenerRequest;
import org.example.dtos.response.UrlShortenerResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final UrlShortenerRepo urlShortenerRepo;

    @Override
    public UrlShortenerResponse generateShortUrl(UrlShortenerRequest urlShortenerRequest) {
        validateUrlRequest(urlShortenerRequest);

        UrlShortener newUrlShortener = new UrlShortener();
        newUrlShortener.setOriginalUrl(urlShortenerRequest.getOriginalUrl());
        newUrlShortener.setShortUrl(withGeneratedUrl(generateRandomLength(), getCharacterRanges()));
        newUrlShortener.setCreatedAt(urlShortenerRequest.getCreatedAt());
        urlShortenerRepo.save(newUrlShortener);

        return mapToUrlShortenerResponse(newUrlShortener);
    }


    @Override
    public UrlShortenerResponse fetchOriginalUrl(UrlShortenerRequest request) {
        //String originalUrl = String.valueOf(urlShortenerRepo.findOriginalUrlByShortUrl(currentShortUrl()));

        UrlShortener urlShortener = urlShortenerRepo.findOriginalUrlByShortUrl(request.getShortUrl());
        confirmContentOf(urlShortener);

        return mapToUrlShortenerResponse(urlShortener);
    }



    @Override
    public UrlShortenerResponse changeOriginalUrl(UrlShortenerRequest request) {
        UrlShortener urlShortener = urlShortenerRepo.findOriginalUrlByShortUrl(request.getShortUrl());
        confirmContentOf(urlShortener);

        urlShortener.setOriginalUrl(request.getOriginalUrl());
        urlShortenerRepo.save(urlShortener);

        return mapToUrlShortenerResponse(urlShortener);
    }



    @Override
    public List<UrlShortenerResponse> viewAllUrls() {
        List<UrlShortener> urlShorteners = urlShortenerRepo.findAll();

        return urlShorteners.stream()
                .map(UrlShortenerServiceImpl::mapToUrlShortenerResponse)
                .collect(Collectors.toList());
    }


    @Override
    public boolean deleteOriginalUrl(UrlShortenerRequest request) {
        UrlShortener urlShortener = urlShortenerRepo.findOriginalUrlByShortUrl(request.getShortUrl());
        confirmContentOf(urlShortener);

        urlShortenerRepo.delete(urlShortener);
        return true;
    }






















    private String currentShortUrl() {
        UrlShortener shortUrl = new UrlShortener();
        return shortUrl.getShortUrl();
    }

    private static void confirmContentOf(UrlShortener urlShortener) {
        if (urlShortener == null) {
            throw new IllegalArgumentException("URL not found");
        }
    }

    private static void validateUrlRequest(UrlShortenerRequest urlShortenerRequest) {
        if (urlShortenerRequest.getOriginalUrl() == null || urlShortenerRequest.getOriginalUrl().isEmpty()) {
            throw new IllegalArgumentException("Original URL cannot be null or empty");
        }
    }

    private String withGeneratedUrl(int length, String[]... ranges) {
        Random random = new Random();
        StringBuilder generatedUrl = new StringBuilder("ice.ly/");

        while (length-- > 0) {
            int randomRangeIndex = random.nextInt(ranges.length);
            String[] range = ranges[randomRangeIndex];
            int min = range[0].charAt(0);
            int max = range[1].charAt(0);
            int randomCharacter = min + random.nextInt(max - min + 1);
            generatedUrl.append((char) randomCharacter);


        }
        return generatedUrl.toString();

    }

    private int generateRandomLength() {
        Random random = new Random();
        return random.nextInt(6, 10);
    }

    private String[][] getCharacterRanges() {
//        Random random = new Random();
        String[][] range;
        range = new String[][]{{"a","z"}, {"0", "9"}, {"A", "Z"}};
//        int randomIndex = random.nextInt(range.length);
        return range;
    }

    private static UrlShortenerResponse mapToUrlShortenerResponse(UrlShortener urlShortener) {
        UrlShortenerResponse response = new UrlShortenerResponse();
        response.setOriginalUrl(urlShortener.getOriginalUrl());
        response.setShortUrl(urlShortener.getShortUrl());
        response.setCreatedAt(urlShortener.getCreatedAt());
        return response;
    }
}

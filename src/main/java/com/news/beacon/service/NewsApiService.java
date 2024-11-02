package com.news.beacon.service;

import com.news.beacon.clients.NewsApiClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NewsApiService {

    final NewsApiClient newsApiClient;

    public NewsApiService(NewsApiClient newsApiClient) {
        this.newsApiClient = newsApiClient;
    }

    public List<Map<String, String>> getHeadlinesByCountryCode(String countryCode) {

        String validCountryCode = Optional.ofNullable(countryCode)
                .filter(code -> code.length() == 2)
                .map(String::toUpperCase)
                .orElseThrow(() -> new IllegalArgumentException("Invalid country code. Must be a non-empty, 2-character string. "));

        return newsApiClient.getTopHeadLinesByCountryCode(validCountryCode).filterNewsHeadlines();
    }
}

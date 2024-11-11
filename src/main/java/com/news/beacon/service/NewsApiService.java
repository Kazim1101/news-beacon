package com.news.beacon.service;

import com.news.beacon.clients.NewsApiClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NewsApiService {

    final NewsApiClient newsApiClient;

    public NewsApiService(NewsApiClient newsApiClient) {
        this.newsApiClient = newsApiClient;
    }

    public List<Map<String, String>> getHeadlinesByCountryCode(String countryCode, String category, String pageSize, String page) {

        Map<String, String> params = new HashMap<>();
        params.put("country", countryCode);
        params.put("category", category);
        params.put("pageSize", pageSize);
        params.put("page", page);

        Optional.ofNullable(countryCode)
                .filter(code -> code.length() == 2)
                .map(String::toUpperCase)
                .orElseThrow(() -> new IllegalArgumentException("Invalid country code. Must be a non-empty, 2-character string. "));

        return newsApiClient.getTopHeadLines(params).filterNewsHeadlines();
    }
}

package com.news.beacon.clients;

import com.news.beacon.entity.NewsApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;

@Component
public class NewApiClientImp implements NewsApiClient {

    private final String newsBaseUrl;
    private final String apiKey;

    @Autowired
    RestTemplate restTemplate;

    public NewApiClientImp(@Value("${client.news.base.url}") final String newsBaseUrl, @Value("${client.news.api.key}") final String apiKey) {
        this.newsBaseUrl = newsBaseUrl;
        this.apiKey = apiKey;

    }

    @Override
    public NewsApiResponse getTopHeadLines(Map<String, String> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(newsBaseUrl + "/top-headlines");

        params.forEach((key, value) ->
                Optional.ofNullable(value)
                        .filter(v -> !v.isEmpty())
                        .ifPresent(v -> builder.queryParam(key, v)));
        builder.queryParam("apiKey", apiKey);

        return restTemplate.getForEntity(builder.build().toUri(), NewsApiResponse.class).getBody();
    }
}

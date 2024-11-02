package com.swagger.example.clients;

import com.swagger.example.entity.NewsApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NewApiClientImp implements NewsApiClient {

    private final String newsBaseUrl;
    private final String apiKey;

    @Autowired
    @Qualifier("news-resttemplate")
    RestTemplate restTemplate;

    public NewApiClientImp(@Value("${client.news.base.url}") final String newsBaseUrl, @Value("${client.news.api.key}") final String apiKey) {
        this.newsBaseUrl = newsBaseUrl;
        this.apiKey = apiKey;

    }

    @Override
    public NewsApiResponse getTopHeadLinesByCountryCode(String countryCode) {

        URI uri = UriComponentsBuilder.fromHttpUrl(newsBaseUrl + "/top-headlines")
            .queryParam("country", countryCode)
            .queryParam("apiKey", apiKey)
            .build().toUri();

        return restTemplate.getForEntity(uri, NewsApiResponse.class).getBody();
    }
}

package com.news.beacon.clients;

import com.news.beacon.entity.GeminiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Component
public class GeminiClientImp implements GeminiClient {

    private final String geminiBaseUrl;
    private final String apiKey;

    @Autowired
    RestTemplate restTemplate;

    public GeminiClientImp(@Value("${client.gemini.base.url}") final String geminiBaseUrl,
                           @Value("${client.gemini.api.key}") final String apiKey) {
        this.geminiBaseUrl = geminiBaseUrl;
        this.apiKey = apiKey;
    }

    public GeminiResponse generateContent(String prompt) {
        String url = UriComponentsBuilder.fromHttpUrl(geminiBaseUrl + "/v1beta/models/gemini-1.5-flash-latest:generateContent")
                .queryParam("key", apiKey)
                .toUriString();

        // Create the request payload
        Map<String, Object> requestPayload = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        // Make the POST request
        return restTemplate.postForEntity(url, requestPayload, GeminiResponse.class).getBody();
    }
}

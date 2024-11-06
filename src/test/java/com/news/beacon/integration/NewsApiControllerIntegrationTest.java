package com.news.beacon.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewsApiControllerIntegrationTest extends RestServerIntegrationTest {

    @Test
    void getHeadlinesByCountryCode() {
        // Perform an actual HTTP request to the running server, which will use the real NewsApiService
        ResponseEntity<List> response = restTemplate.getForEntity(
                baseUrl + "/news/headlines?country=us",
                List.class
        );

        // Validate the response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Validate the response body - for example, checking that it's not null and contains expected data
        List responseBody = response.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty(), "Actual value should be greater than expected minimum");
    }
}
package com.swagger.example.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class NewApiClientConfig {

    @Bean("news-resttemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

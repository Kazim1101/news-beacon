package com.swagger.example.clients;

import com.swagger.example.entity.NewsApiResponse;

public interface NewsApiClient {

    NewsApiResponse getTopHeadLinesByCountryCode(String countryCode);

}

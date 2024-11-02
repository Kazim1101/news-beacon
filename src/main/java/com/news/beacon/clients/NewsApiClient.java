package com.news.beacon.clients;

import com.news.beacon.entity.NewsApiResponse;

public interface NewsApiClient {

    NewsApiResponse getTopHeadLinesByCountryCode(String countryCode);

}

package com.news.beacon.clients;

import com.news.beacon.entity.NewsApiResponse;

import java.util.Map;

public interface NewsApiClient {

    NewsApiResponse getTopHeadLines(Map<String, String> params);

}

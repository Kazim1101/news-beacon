package com.news.beacon.clients;

import com.news.beacon.entity.GeminiResponse;
import com.news.beacon.entity.NewsApiResponse;

import java.util.Map;

public interface GeminiClient {

    GeminiResponse generateContent(String prompt);

}

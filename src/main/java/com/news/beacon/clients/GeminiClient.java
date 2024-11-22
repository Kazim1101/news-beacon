package com.news.beacon.clients;

import com.news.beacon.entity.GeminiResponse;

public interface GeminiClient {

    GeminiResponse generateContent(String prompt);

}

package com.swagger.example.controller;

import com.swagger.example.entity.NewsApiResponse;
import com.swagger.example.service.NewsApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/news")
public class NewsApiController {

    private final NewsApiService newsApiService;

    @GetMapping(value = "/headlines", produces = {"application/json"})
    public ResponseEntity<List<Map<String, String>>> getHeadlinesByCountryCode(@RequestParam("country") String countryCode) {
        return ResponseEntity.ok(newsApiService.getHeadlinesByCountryCode(countryCode));
    }


}

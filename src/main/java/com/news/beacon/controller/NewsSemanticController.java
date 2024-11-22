package com.news.beacon.controller;

import com.news.beacon.entity.NewsSemanticResponse;
import com.news.beacon.entity.NewsSemanticRequest;
import com.news.beacon.service.NewsSemanticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/news/semantic")
public class NewsSemanticController {

    private final NewsSemanticService newsSemanticService;

    @PostMapping(value = "/headlines", produces = {"application/json"})
    public ResponseEntity<List<NewsSemanticResponse>> getHeadlinesByCountryCode(@RequestBody List<NewsSemanticRequest> newsSemanticRequest) {
        return ResponseEntity.ok(newsSemanticService.getSemanticAnalysis(newsSemanticRequest));
    }


}

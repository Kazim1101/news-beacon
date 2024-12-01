package com.news.beacon.entity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
public class NewsApiResponse {
    // Getters and Setters
    private String status;
    private int totalResults;
    private List<Article> articles;

    public List<Map<String,String>> filterNewsHeadlines(){
        return articles.stream()
                .filter(Objects::nonNull)  // Ensure the article is not null
                .map(article -> {
                    return Map.of(
                            "newsId", UUID.randomUUID().toString(),
                            "author", article.getAuthor() != null ? article.getAuthor() : "Unknown Author",
                            "title", article.getTitle() != null ? article.getTitle() : "Untitled",
                            "description", article.getDescription() != null ? article.getDescription() : "No Description",
                            "source", article.getSource() != null ? article.getSource().getName() : "Unknown Source",
                            "articleUrl", article.getUrl() != null ? article.getUrl() : "No URL",
                            "publishedAt", article.getPublishedAt() != null ? article.getPublishedAt() : "Not Available"
                    );
                })
                .toList();
    }

}

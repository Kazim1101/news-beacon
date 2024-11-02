package com.news.beacon.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Article {
    // Getters and Setters
    private Source source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

}

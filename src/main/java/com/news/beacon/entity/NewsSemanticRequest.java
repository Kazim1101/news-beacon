package com.news.beacon.entity;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class NewsSemanticRequest {
    private String newsId;
    private String publishedAt;
    private String source;
    private String title;
    private String description;
    private String articleUrl;
    private String author;

}

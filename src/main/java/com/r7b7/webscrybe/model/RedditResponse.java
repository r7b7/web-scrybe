package com.r7b7.webscrybe.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RedditResponse {
    private String topicTitle;
    private String topicContent;
}

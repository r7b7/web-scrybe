package com.r7b7.webscrybe.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.r7b7.webscrybe.client.RedditClient;
import com.r7b7.webscrybe.exception.RedditApiException;
import com.r7b7.webscrybe.model.ApiResponse;
import com.r7b7.webscrybe.model.RedditResponse;

@Service
public class RedditService {
    Logger logger = LoggerFactory.getLogger(RedditService.class);

    private RedditClient redditClient;

    public RedditService(RedditClient redditClient) {
        this.redditClient = redditClient;
    }

    public ApiResponse<?> fetchRedditHotContent(String subreddit, Integer limit) {
        try {
            var response = redditClient.getHotTopics(subreddit, limit);
            List<RedditResponse> result = response.getData().getChildren().stream()
                    .map(child -> new RedditResponse(child.getData().getTitle(), child.getData().getSelftext()))
                    .toList();
            return ApiResponse.success("Success", result);
        } catch (RedditApiException ex) {
            return ApiResponse.error("Reddit Call Failed: " + ex.getMessage());
        } catch (Exception ex) {
            return ApiResponse.error("Unexpected Error Occurred: " + ex.getMessage());
        }
    }
}

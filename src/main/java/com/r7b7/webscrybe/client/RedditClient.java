package com.r7b7.webscrybe.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.r7b7.webscrybe.config.AppConfigProperties;
import com.r7b7.webscrybe.config.Constants;
import com.r7b7.webscrybe.entity.ListingResponse;
import com.r7b7.webscrybe.exception.RedditApiException;

@Component
public class RedditClient {
    Logger logger = LoggerFactory.getLogger(RedditClient.class);

    @Qualifier("redditWebClient")
    private final WebClient redditWebClient;

    private final AppConfigProperties appConfigProperties;

    public RedditClient(WebClient redditWebClient, AppConfigProperties appConfigProperties) {
        this.redditWebClient = redditWebClient;
        this.appConfigProperties = appConfigProperties;
    }

    public ListingResponse getHotTopics(String subreddit, Integer limit) {
        String url = UriComponentsBuilder
                .fromHttpUrl(appConfigProperties.getRedditTopicUrlPrefix() + subreddit
                        + appConfigProperties.getRedditTopicUrlSuffix())
                .queryParam(Constants.LIMIT, limit)
                .toUriString();

        return redditWebClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ListingResponse.class)
                .doOnError(error -> logger.info("Error occurred: " + error.getMessage()))
                .onErrorMap(error -> {
                    return new RedditApiException("Unexpected error: " + error.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR);
                })
                .block();

    }

}

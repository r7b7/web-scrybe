package com.r7b7.webscrybe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;


@Component
@Getter
public class AppConfigProperties {
    @Value("${google.url}")
    private String googleUrl;

    @Value("${bing.url}")
    private String bingUrl;

    @Value("${search.sleep}")
    private long sleepTime;

    @Value("${reddit.auth.url}")
    private String redditAuthUrl;

    @Value("${reddit.topic.url.prefix}")
    private String redditTopicUrlPrefix;

    @Value("${reddit.topic.url.suffix}")
    private String redditTopicUrlSuffix;

    @Value("${duckduckgo.url}")
    private String duckDuckGoUrl;
}

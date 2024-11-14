package com.r7b7.webscrybe.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RedditRequest {
    @Schema(description = "Name of the subreddit group", example = "ClaudeAI", required = true)
    @NotBlank(message = "subreddit value is required")
    private String subreddit;

    @Schema(description = "Max number of hot topics to fetch", example = "2", required = false)
    @Min(value = 0)
    @Max(value = 10)
    private Integer limit = 5;
}

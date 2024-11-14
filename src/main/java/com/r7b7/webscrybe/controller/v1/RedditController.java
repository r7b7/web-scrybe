package com.r7b7.webscrybe.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r7b7.webscrybe.model.ApiResponse;
import com.r7b7.webscrybe.model.RedditRequest;
import com.r7b7.webscrybe.service.RedditService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/v1")
public class RedditController {
    private RedditService redditService;

    public RedditController(RedditService redditService) {
        this.redditService = redditService;
    }

    @Operation(summary = "Get Hot Content by SubReddit", description = "Fetch title and full text from reddit groups")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "411", description = "Content Length Issue", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
    })

    @GetMapping(value = "/reddit/hot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> fetchRedditHotContent(
            @Parameter(description = "Request data") @Valid @ModelAttribute RedditRequest request) {
        ApiResponse<?> response = redditService.fetchRedditHotContent(request.getSubreddit(), request.getLimit());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
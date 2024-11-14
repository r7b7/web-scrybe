package com.r7b7.webscrybe.controller.v1;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.r7b7.webscrybe.model.ApiResponse;
import com.r7b7.webscrybe.model.RedditResponse;
import com.r7b7.webscrybe.service.RedditService;

public class RedditControllerTest {
    private MockMvc mockMvc;

    @Mock
    private RedditService redditService;

    @InjectMocks
    private RedditController redditController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(redditController).build();
    }

    @Test
    public void fetchRedditHotContent_validRequest_returnsApiResponse() throws Exception {
        // Mock
        List<RedditResponse> result = new ArrayList<>();
        RedditResponse mockRedditResponse = new RedditResponse("mockTitle", "mockContent");
        result.add(mockRedditResponse);
        ApiResponse<?> response = ApiResponse.success("Success", result);
        Mockito.doReturn(response).when(redditService).fetchRedditHotContent(anyString(), anyInt());

        // Assert
        mockMvc.perform(get("/api/v1/reddit/hot")
                .param("subreddit", "java")
                .param("limit", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    public void fetchRedditHotContent_invalidRequest_returnsBadRequest() throws Exception {
        // Assert
        mockMvc.perform(get("/api/v1/reddit/hot")
                .param("subreddit", "java")
                .param("limit", "1001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

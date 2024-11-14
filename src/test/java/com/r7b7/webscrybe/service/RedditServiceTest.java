package com.r7b7.webscrybe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.r7b7.webscrybe.client.RedditClient;
import com.r7b7.webscrybe.entity.Child;
import com.r7b7.webscrybe.entity.ChildData;
import com.r7b7.webscrybe.entity.ListingData;
import com.r7b7.webscrybe.entity.ListingResponse;
import com.r7b7.webscrybe.exception.RedditApiException;
import com.r7b7.webscrybe.model.ApiResponse;
import com.r7b7.webscrybe.service.RedditService;

public class RedditServiceTest {
    @Mock
    private RedditClient redditClient;

    @InjectMocks
    private RedditService redditService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchRedditHotContent_SuccessfulResponse_ReturnsApiResponse() {
        ListingResponse res = getChildList();
        when(redditClient.getHotTopics(anyString(), anyInt())).thenReturn(res);

        ApiResponse<?> response = redditService.fetchRedditHotContent("java", 5);

        assertTrue(response.isSuccess());
        assertEquals("Success", response.getMessage());
        assertEquals(1, ((List<?>) response.getData()).size());
    }

    @Test
    void fetchRedditHotContent_RedditApiException_ReturnsErrorResponse() {
        when(redditClient.getHotTopics(anyString(), anyInt()))
                .thenThrow(new RedditApiException("API error", null));

        ApiResponse<?> response = redditService.fetchRedditHotContent("java", 5);

        assertFalse(response.isSuccess());
        assertEquals("Reddit Call Failed: API error", response.getError());
    }

    @Test
    void fetchRedditHotContent_UnexpectedException_ReturnsErrorResponse() {
        when(redditClient.getHotTopics(anyString(), anyInt()))
                .thenThrow(new RuntimeException("Unexpected error"));

        ApiResponse<?> response = redditService.fetchRedditHotContent("java", 5);

        assertFalse(response.isSuccess());
        assertEquals("Unexpected Error Occurred: Unexpected error", response.getError());
    }

    private ListingResponse getChildList() {
        ListingResponse res = new ListingResponse();
        ListingData data = new ListingData();
        Child child = new Child();
        ChildData childData = new ChildData();
        childData.setSelftext("Mock Self text");
        childData.setTitle("Mock Title");
        child.setData(childData);
        List<Child> childList = new ArrayList<>();
        childList.add(child);
        data.setChildren(childList);
        res.setData(data);
        return res;
    }
}

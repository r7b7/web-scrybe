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

import com.r7b7.webscrybe.controller.v1.SearchController;
import com.r7b7.webscrybe.model.DriverType;
import com.r7b7.webscrybe.model.SearchResponse;
import com.r7b7.webscrybe.service.DriverServiceFactory;
import com.r7b7.webscrybe.service.GoogleSearchService;
import com.r7b7.webscrybe.service.IBaseService;

public class SearchControllerTest {
    private MockMvc mockMvc;

    @Mock
    private GoogleSearchService service;

    @Mock
    private DriverServiceFactory driverServiceFactory;

    @InjectMocks
    private SearchController searchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    public void getWebSearchResults_validRequest_returnsApiResponse() throws Exception {
        // Mock
        List<SearchResponse> response = new ArrayList<>();
        SearchResponse res = new SearchResponse("mockTitle", "mockUrl");
        response.add(res);
        IBaseService baseService = new GoogleSearchService(null);
        Mockito.doReturn(baseService).when(driverServiceFactory).getDriverService(DriverType.GOOGLE);
        Mockito.doReturn(response).when(service).getResults(anyString(), anyInt());

        // Assert
        mockMvc.perform(get("/api/v1/search")
                .param("query", "java")
                .param("driver", "GOOGLE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    public void getWebSearchResults_invalidRequest_returnsBadRequest() throws Exception {
        // Assert
        mockMvc.perform(get("/api/v1/search")
                .param("driver", "GOOGLE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}

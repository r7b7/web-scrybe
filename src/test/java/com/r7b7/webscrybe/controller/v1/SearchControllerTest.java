package com.r7b7.webscrybe.controller.v1;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        IBaseService mockService = Mockito.mock(IBaseService.class);
        Mockito.when(driverServiceFactory.getDriverService(Mockito.any(DriverType.class)))
                .thenReturn(mockService);

        Mockito.when(mockService.getResults(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(ApiResponse.success("success", List.of(new SearchResponse("", ""))));

        // Assert
        mockMvc.perform(get("/api/v1/search")
                .param("query", "java")
                .param("driver", "GOOGLE")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.success").value(true));
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

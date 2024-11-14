package com.r7b7.webscrybe.controller.v1;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r7b7.webscrybe.model.ApiResponse;
import com.r7b7.webscrybe.model.DriverType;
import com.r7b7.webscrybe.model.SearchResponse;
import com.r7b7.webscrybe.service.DriverServiceFactory;
import com.r7b7.webscrybe.service.IBaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1")
public class SearchController {

    private DriverServiceFactory driverServiceFactory;

    public SearchController(DriverServiceFactory driverServiceFactory) {
        this.driverServiceFactory = driverServiceFactory;
    }

    @Operation(summary = "Get Hot Content by SubReddit", description = "Fetch title and full text from reddit groups")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Search Results", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    })

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> getWebSearchResults(
            @Parameter(description = "Search Query", example = "AI trends latest") @RequestParam(required = true, name = "query") String query,
            @Parameter(description = "Search Driver", example = "GOOGLE") @RequestParam(required = false, name = "driver", defaultValue = "GOOGLE") DriverType driver,
            @Parameter(description = "Max results to return", example = "10") @RequestParam(required = false, name = "max_count", defaultValue = "10") String maxCount) {

        IBaseService service = driverServiceFactory.getDriverService(driver);
        List<SearchResponse> responseBody = service.getResults(query, Integer.valueOf(maxCount));
        return new ResponseEntity<>(ApiResponse.success("Success", responseBody), HttpStatus.OK);
    }
}

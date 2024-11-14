package com.r7b7.webscrybe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class SearchRequest {
    @NonNull
    private String driver;
    @NonNull
    private String query;
    private Integer maxCount=10;
}

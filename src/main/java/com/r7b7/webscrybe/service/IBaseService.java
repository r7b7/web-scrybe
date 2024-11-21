package com.r7b7.webscrybe.service;

import java.util.List;

import com.r7b7.webscrybe.model.ApiResponse;
import com.r7b7.webscrybe.model.SearchResponse;

public interface IBaseService{
    public ApiResponse<List<SearchResponse>> getResults(String query, int maxCount);   
}

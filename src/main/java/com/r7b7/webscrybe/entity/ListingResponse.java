package com.r7b7.webscrybe.entity;

import lombok.Data;

@Data
public class ListingResponse {
    private String kind;
    private ListingData data;
}

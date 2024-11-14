package com.r7b7.webscrybe.entity;

import java.util.List;

import lombok.Data;

@Data
public class ListingData {
     private String after;
    private int dist;
    private String modhash;
    private List<Child> children;
}

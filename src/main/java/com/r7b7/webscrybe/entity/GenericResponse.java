package com.r7b7.webscrybe.entity;

import lombok.Data;

@Data
public class GenericResponse<T> {
    T data;
}

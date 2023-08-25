package com.example.demo.utils;

public interface Mapper<A, B> {
    A mapEntityToDto(B source);
}

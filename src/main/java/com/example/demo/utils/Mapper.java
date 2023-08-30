package com.example.demo.utils;

import java.util.List;

public interface Mapper<A, B> {
    A mapEntityToDto(B source);

    B mapDtoToEntity(A source);

    List<A> mapListEntityToDto(List<B> source);
}
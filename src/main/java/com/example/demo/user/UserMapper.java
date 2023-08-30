package com.example.demo.user;

import com.example.demo.utils.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper implements Mapper<UserDto, UserEntity> {
    @Override
    public UserDto mapEntityToDto(UserEntity source) {
        return UserDto.builder()
                .id(source.getId())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .username(source.getUsername())
                .email(source.getEmail())
                .password(source.getPassword())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .notes(source.getNotes())
                .build();
    }

    @Override
    public UserEntity mapDtoToEntity(UserDto source) {
        return UserEntity.builder()
                .id(source.getId())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .username(source.getUsername())
                .email(source.getEmail())
                .password(source.getPassword())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .notes(source.getNotes())
                .build();
    }

    @Override
    public List<UserDto> mapListEntityToDto(List<UserEntity> source) {
        return source.stream()
                .map(this::mapEntityToDto)
                .toList();
    }
}
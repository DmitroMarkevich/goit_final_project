package com.example.demo.user;

import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();
    @Test
    public void testMapEntityToDto() {
        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        entity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        entity.setUsername("testUser");
        entity.setEmail("test@example.com");
        entity.setPassword("hashedPassword");
        entity.setFirstName("NameOfUser");
        entity.setLastName("LastNameOfUser");

        UserDto dto = userMapper.mapEntityToDto(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getCreatedAt(), dto.getCreatedAt());
        assertEquals(entity.getUpdatedAt(), dto.getUpdatedAt());
        assertEquals(entity.getUsername(), dto.getUsername());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getPassword(), dto.getPassword());
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
    }

    @Test
    public void testMapDtoToEntity() {
        UserDto dto = new UserDto();
        dto.setId(UUID.randomUUID());
        dto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        dto.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        dto.setUsername("testUser");
        dto.setEmail("test@example.com");
        dto.setPassword("plainPassword");
        dto.setFirstName("UserName");
        dto.setLastName("LastNameOfUser");

        UserEntity entity = userMapper.mapDtoToEntity(dto);
        
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getCreatedAt(), entity.getCreatedAt());
        assertEquals(dto.getUpdatedAt(), entity.getUpdatedAt());
        assertEquals(dto.getUsername(), entity.getUsername());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
    }
}


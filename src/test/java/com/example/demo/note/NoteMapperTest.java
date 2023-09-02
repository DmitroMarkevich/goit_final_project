package com.example.demo.note;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoteMapperTest {
    private final NoteMapper noteMapper = new NoteMapper();

    @Test
    public void testMapEntityToDto() {
        NoteEntity entity = NoteEntity.builder()
                .id(UUID.randomUUID())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .title("Test Note")
                .content("It's my test note!")
                .accessType(AccessType.PRIVATE)
                .build();

        NoteDto dto = noteMapper.mapEntityToDto(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getCreatedAt(), dto.getCreatedAt());
        assertEquals(entity.getUpdatedAt(), dto.getUpdatedAt());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getContent(), dto.getContent());
        assertEquals(entity.getAccessType(), dto.getAccessType());
    }

    @Test
    public void testMapDtoToEntity() {
        NoteDto dto = NoteDto.builder()
                .id(UUID.randomUUID())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .title("Test Note")
                .content("It's my test note!")
                .accessType(AccessType.PRIVATE)
                .build();

        NoteEntity entity = noteMapper.mapDtoToEntity(dto);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getCreatedAt(), dto.getCreatedAt());
        assertEquals(entity.getUpdatedAt(), dto.getUpdatedAt());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getContent(), dto.getContent());
        assertEquals(entity.getAccessType(), dto.getAccessType());
    }
}

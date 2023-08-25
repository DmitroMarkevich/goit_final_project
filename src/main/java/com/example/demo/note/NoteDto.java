package com.example.demo.note;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
public class NoteDto {
    private UUID id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String title;
    private String content;
    private AccessType accessType;
    private UUID userId;
}

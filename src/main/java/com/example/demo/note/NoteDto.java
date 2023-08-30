package com.example.demo.note;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class NoteDto {
    private UUID id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String title;
    private String content;
    private AccessType accessType;
    private UUID userId;
    private String username;
    private Boolean editable;
}

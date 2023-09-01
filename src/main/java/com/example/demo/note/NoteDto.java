package com.example.demo.note;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class NoteDto {
    private UUID id;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @Size(min = 5, max = 100, message = "Error - Enter a title between 5 and 100 characters!")
    private String title;

    @Size(min = 5, max = 500, message = "Error - Enter content between 5 and 500 characters!")
    private String content;

    private AccessType accessType;

    private UUID userId;
}
package com.example.demo.user;

import com.example.demo.note.NoteEntity;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@Data
@Builder
public class UserDto {
    private UUID id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Collection<NoteEntity> notes;
}

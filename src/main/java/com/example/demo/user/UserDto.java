package com.example.demo.user;

import com.example.demo.note.NoteEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDto {
    private UUID id;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
    private String username;

    @Size(max = 50)
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^.{5,}@.*$", message = "Email must have at least 5 characters before @")
    private String email;

    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    @Size(max = 50, message = "First name must be at most 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name must be at most 50 characters")
    private String lastName;

    private List<NoteEntity> notes;
}

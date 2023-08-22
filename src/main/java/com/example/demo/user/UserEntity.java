package com.example.demo.user;

import com.example.demo.note.NoteEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column
    @Length(min = 5, max = 50)
    private String username;

    @Column
    @Length(min = 5, max = 50)
    private String email;

    @Column
    @Length(min = 8, max = 100)
    private String password;

    @Length(max = 50)
    @Column(name = "first_name")
    private String firstName;

    @Length(max = 50)
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "noteOwner", fetch = FetchType.LAZY)
    private Collection<NoteEntity> notes;
}

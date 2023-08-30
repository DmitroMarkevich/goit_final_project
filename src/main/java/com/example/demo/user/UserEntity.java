package com.example.demo.user;

import com.example.demo.note.NoteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
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

    @JoinColumn(name = "user_id")
    @OneToMany(fetch = FetchType.EAGER)
    private List<NoteEntity> notes;
}
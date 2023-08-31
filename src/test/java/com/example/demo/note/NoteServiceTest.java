package com.example.demo.note;

import com.example.demo.exception.note.NoteNotFoundException;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserEntity;
import com.example.demo.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserService userService;

    @Mock
    private NoteMapper noteMapper;

    private NoteService noteService;
    private UserDto user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        noteService = new NoteService(noteRepository, userService, noteMapper);

        user = new UserDto();
        user.setId(UUID.fromString("461e8eda-9e13-4051-a677-3dbf4ad5b2d8"));
        user.setEmail("aaaaaaaaaaa");
        user.setUsername("aaaaaaaaaaa");
        user.setPassword("aaaaaaaaaaa");
        user.setNotes(new ArrayList<>());
    }

    @Test
    void testGetAllNotes() {
        List<NoteEntity> notes = new ArrayList<>();

        when(userService.getUser()).thenReturn(user);

        // Mock mapper
        List<NoteDto> noteDtos = new ArrayList<>();

        when(noteMapper.mapListEntityToDto(notes)).thenReturn(noteDtos);

        // Test
        List<NoteDto> result = noteService.getAllNotes();

        // Verify
        assertEquals(noteDtos, result);
    }

    @Test
    public void testCreateNote() {
        // Prepare test data
        NoteDto noteDto = new NoteDto();

        when(userService.getUser()).thenReturn(user);

        // Mock NoteMapper's behavior
        NoteEntity mappedEntity = new NoteEntity();
        when(noteMapper.mapDtoToEntity(noteDto)).thenReturn(mappedEntity);

        // Call the method to be tested
        noteService.createNote(noteDto);

        // Verify that methods were called with expected arguments
        ArgumentCaptor<NoteEntity> entityCaptor = ArgumentCaptor.forClass(NoteEntity.class);
        verify(noteRepository).save(entityCaptor.capture());

        NoteEntity savedEntity = entityCaptor.getValue();
        assertEquals(user.getId(), savedEntity.getUserId());
        // Add more assertions for other fields if needed
    }

}
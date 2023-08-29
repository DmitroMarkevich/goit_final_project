package com.example.demo.note;

import com.example.demo.exception.note.NoteNotFoundException;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;
    private final NoteMapper noteMapper;

    public List<NoteDto> getAllNotes() {
        return noteMapper.mapListEntityToDto(userService.getUser().getNotes());
    }

    public void createNote(NoteDto noteDto) {
        noteRepository.save(noteMapper.mapDtoToEntity(noteDto).toBuilder()
                .userId(userService.getUser().getId())
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build()
        );
    }

    public NoteEntity getById(UUID id) throws NoteNotFoundException {
        Optional<NoteEntity> optionalNote = noteRepository.findById(id);

        if (optionalNote.isPresent()) {
            return optionalNote.get();
        } else {
            throw new NoteNotFoundException(id);
        }
    }

    public void updateNote(NoteDto noteDto) throws NoteNotFoundException {
        NoteEntity updatedNote = getById(noteDto.getId());
        updatedNote.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        updatedNote.setTitle(noteDto.getTitle());
        updatedNote.setContent(noteDto.getContent());
        updatedNote.setAccessType(noteDto.getAccessType());
        noteRepository.save(updatedNote);
    }

    public void deleteById(UUID id) throws NoteNotFoundException {
        Optional<NoteEntity> optionalNote = noteRepository.findById(id);

        if (optionalNote.isPresent()) {
            noteRepository.delete(optionalNote.get());
        } else {
            throw new NoteNotFoundException(id);
        }
    }
}
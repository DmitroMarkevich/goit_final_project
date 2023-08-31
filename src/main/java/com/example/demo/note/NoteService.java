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

    public NoteDto getById(UUID id) throws NoteNotFoundException {
        Optional<NoteEntity> optionalNote = noteRepository.findById(id);

        if (optionalNote.isEmpty()) {
            throw new NoteNotFoundException(id);
        }

        NoteEntity noteEntity = optionalNote.get();

        if (!userService.getUser().getNotes().contains(noteEntity)) {
            throw new NoteNotFoundException(id);
        }

        return noteMapper.mapEntityToDto(noteEntity);
    }

    public void updateNote(NoteDto noteDto) throws NoteNotFoundException {
        noteRepository.save(noteMapper.mapDtoToEntity(getById(noteDto.getId()).toBuilder()
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .accessType(noteDto.getAccessType())
                .build())
        );
    }

    public void deleteById(UUID id) throws NoteNotFoundException {
        noteRepository.delete(noteMapper.mapDtoToEntity(getById(id)));
    }

    public boolean canShare(NoteDto noteDto) {
        boolean isPrivate = noteDto.getAccessType() == AccessType.PRIVATE;
        boolean userExists = userService.getUser() != null;
        boolean isUserOwner = userExists && userService.getUser().getId().equals(noteDto.getUserId());

        return !isPrivate || (userExists && isUserOwner);
    }
}
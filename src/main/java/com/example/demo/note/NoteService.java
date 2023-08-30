package com.example.demo.note;

import com.example.demo.exception.note.NoteNotFoundException;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;
    private final NoteMapper noteMapper;

    public List<NoteDto> getAllNotes() {
        UserDto userDto = userService.getUser();
        Stream<NoteDto> myNotesStream = noteMapper.mapListEntityToDto(userDto.getNotes()).stream()
                .peek(note -> note.setEditable(true));
        Stream<NoteDto> allPublicNotesStream = noteMapper.mapListEntityToDto(noteRepository.findByAccessTypeAndUserIdIsNot(AccessType.PUBLIC, userDto.getId())).stream()
                .peek(note -> note.setEditable(false));
        return Stream.concat(myNotesStream, allPublicNotesStream)
                .collect(Collectors.toList());
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

        if (optionalNote.isPresent()) {
            return noteMapper.mapEntityToDto(optionalNote.get());
        } else {
            throw new NoteNotFoundException(id);
        }
    }

    public void updateNote(NoteDto noteDto) throws NoteNotFoundException {
        noteRepository.save(noteMapper.mapDtoToEntity(getById(noteDto.getId())
                .toBuilder()
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .accessType(noteDto.getAccessType())
                .build())
        );
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
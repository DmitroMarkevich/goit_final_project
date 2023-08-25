package com.example.demo.note;


import com.example.demo.exception.NoteNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;


    public List<NoteEntity> getAll() {
        return noteRepository.findAll();
    }

    public NoteEntity addNote(NoteEntity noteEntity) {
        return noteRepository.save(noteEntity);
    }

    public NoteEntity getById(UUID id) throws NoteNotFoundException {
        Optional<NoteEntity> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            return optionalNote.get();
        } else {
            throw new NoteNotFoundException(id);
        }
    }

    public void updateNote(NoteEntity noteEntity) throws NoteNotFoundException {
        NoteEntity ee = getById(noteEntity.getId());
        ee.setTitle(noteEntity.getTitle());
        ee.setContent(noteEntity.getContent());
        noteRepository.save(ee);
    }

    public void deleteById(UUID id) throws NoteNotFoundException {
        Optional<NoteEntity> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            noteRepository.deleteById(id);
        } else {
            throw new NoteNotFoundException(id);
        }

    }


}

package com.example.demo.note;

import com.example.demo.exception.note.NoteValidationException;
import org.springframework.stereotype.Component;

@Component
public class NoteValidator {
    public void validate(NoteDto noteDto) throws NoteValidationException {
        if (!isValidTitle(noteDto.getTitle()) || !isValidContent(noteDto.getContent())) {
            throw new NoteValidationException(noteDto.getId());
        }
    }

    private boolean isValidTitle(String noteTitle) {
        return noteTitle.length() <= 100 && noteTitle.length() >= 5;
    }

    private boolean isValidContent(String noteContent) {
        return noteContent.length() <= 500 && noteContent.length() >= 5;
    }
}

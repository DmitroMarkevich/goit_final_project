package com.example.demo.exception.note;

import java.util.UUID;

public class NoteValidationException extends Exception {
    private static final String NOTE_NOT_VALID_EXCEPTION_TEXT = "Note with id = %s not didn't pass validation.";

    public NoteValidationException(UUID noteId) {
        super(String.format(NOTE_NOT_VALID_EXCEPTION_TEXT, noteId));
    }
}

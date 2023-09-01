package com.example.demo.note;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NoteValidator implements Validator {
    private static final int MIN_CONTENT_LENGTH = 5;
    private static final int MAX_CONTENT_LENGTH = 500;
    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MAX_TITLE_LENGTH = 100;

    @Override
    public boolean supports(Class<?> clazz) {
        return NoteDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof NoteDto noteDto) {
            validateContent(noteDto.getContent(), errors);
            validateTitle(noteDto.getTitle(), errors);
        }
    }

    private void validateContent(String content, Errors errors) {
        if (content.length() < MIN_CONTENT_LENGTH || content.length() > MAX_CONTENT_LENGTH) {
            errors.rejectValue("content", "note.content.invalid", "Помилка - введіть вміст нотатки від " + MIN_CONTENT_LENGTH + " до " + MAX_CONTENT_LENGTH + " символів!");
        }
    }

    private void validateTitle(String title, Errors errors) {
        if (title.length() < MIN_TITLE_LENGTH || title.length() > MAX_TITLE_LENGTH) {
            errors.rejectValue("title", "note.title.invalid", "Помилка - введіть назву нотатки від " + MIN_TITLE_LENGTH + " до " + MAX_TITLE_LENGTH + " символів!");
        }
    }
}
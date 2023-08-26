package com.example.demo.note;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NoteValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return NoteDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NoteDto noteDto = (NoteDto) target;
        String noteContent = noteDto.getContent();
        String noteTitle = noteDto.getTitle();

        if (noteContent.length() <= 500 && noteContent.length() >= 5) {
            errors.rejectValue("content", "note.content.invalid", "Помилка - введіть вміст нотатки від 5 до 500 символів!");
        }
        if (noteTitle.length() <= 100 && noteTitle.length() >= 5) {
            errors.rejectValue("title", "note.title.invalid", "Помилка - введіть назву нотатки від 5 до 100 символів!");
        }
    }
}

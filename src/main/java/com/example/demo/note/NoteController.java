package com.example.demo.note;

import com.example.demo.exception.note.NoteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/list")
    public ModelAndView getAllNotes() {
        ModelAndView result = new ModelAndView("note/notes");
        result.addObject("allNotes", noteService.getAll()); // noteService.getAll() - test
        return result;
    }

//    @GetMapping("/add")
//    public ModelAndView getAddForm() {
//        return new ModelAndView("note/editor");
//    }

    @PostMapping("/add")
    public RedirectView addNote(@RequestParam String title, @RequestParam String content) {
        noteService.addNote(NoteEntity.builder()
                .title(title)
                .content(content)
                .accessType(AccessType.PRIVATE)
                .userId(UUID.fromString("461e8eda-9e13-4051-a677-3dbf4ad5b2d8")) // test!!!
                .build()
        );
        return new RedirectView("/note/list");
    }

    @GetMapping("/edit")
    public ModelAndView getEditForm(@RequestParam UUID id) throws NoteNotFoundException {
        ModelAndView result = new ModelAndView("note/editor");
        result.addObject("editNote", noteService.getById(id));
        return result;
    }

    @PostMapping("/edit")
    public RedirectView saveChanges(@ModelAttribute NoteEntity note) throws NoteNotFoundException {
        noteService.updateNote(note);
        return new RedirectView("/note/list");
    }

    @PostMapping("/delete")
    public RedirectView deleteNote(@RequestParam UUID id) throws NoteNotFoundException {
        noteService.deleteById(id);
        return new RedirectView("/note/list");
    }
}
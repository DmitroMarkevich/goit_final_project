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
@RequestMapping(value = "/note")
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/list")
    public ModelAndView getAllNotes() {
        ModelAndView result = new ModelAndView("notes");
        result.addObject("allNotes", noteService.getAll());
        return result;
    }

    @GetMapping("/add")
    public ModelAndView getAddForm() {
        return new ModelAndView("editor");
    }

    @PostMapping("/add")
    public RedirectView addNote(@RequestParam String title, @RequestParam String content) {
        NoteEntity note = new NoteEntity();
        note.setTitle(title);
        note.setContent(content);
        noteService.addNote(note);
        return new RedirectView("/note/list");
    }

    @GetMapping("/edit")
    public ModelAndView getEditForm(@RequestParam UUID id) throws NoteNotFoundException {
        ModelAndView result = new ModelAndView("editor");
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
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
    private static final String REDIRECT_LIST = "/note/list";
    private final NoteService noteService;

    @GetMapping("/list")
    public ModelAndView listAllNotes() {
        return new ModelAndView(REDIRECT_LIST).addObject("allNotes", noteService.getAll());
    }

    @GetMapping("/create")
    public ModelAndView getAddForm() {
        return new ModelAndView("note/create");
    }

    @PostMapping("/create")
    public RedirectView createNote(@ModelAttribute NoteEntity note) {
        noteService.createNote(note);
        return new RedirectView(REDIRECT_LIST);
    }

    @GetMapping("/edit")
    public ModelAndView showEditNoteForm(@RequestParam UUID id) throws NoteNotFoundException {
        return new ModelAndView("note/editor").addObject("editNote", noteService.getById(id));
    }

    @PostMapping("/edit")
    public RedirectView saveNoteChanges(@ModelAttribute NoteEntity note) throws NoteNotFoundException {
        noteService.updateNote(note);
        return new RedirectView(REDIRECT_LIST);
    }

    @PostMapping("/delete")
    public RedirectView deleteNote(@RequestParam UUID id) throws NoteNotFoundException {
        noteService.deleteById(id);
        return new RedirectView(REDIRECT_LIST);
    }

    @GetMapping("/share")
    public ModelAndView showShareNoteForm(@RequestParam UUID id) {
        return new ModelAndView(/* test */);
    }
}
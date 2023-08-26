package com.example.demo.note;

import com.example.demo.exception.note.NoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.util.UUID;

@Controller
@RequestMapping(value = "/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/list")
    public ModelAndView getAllNotes(){
        ModelAndView result = new ModelAndView("notes");
        result.addObject("allNotes", noteService.getAll());
        return result;
    }

    @PostMapping("/add")
    public RedirectView addNote(@ModelAttribute NoteEntity note) {
        noteService.addNote(note);
        return new RedirectView("/note/list");
    }

    @GetMapping("/edit")
    public ModelAndView getEditForm(@RequestParam UUID id) throws NoteNotFoundException {
        ModelAndView result = new ModelAndView("editor");
        result.addObject("editNote", noteService.getById(id));
        return result;
    }

    @PostMapping(value = "/edit")
    public RedirectView saveChanges(@ModelAttribute NoteEntity note) throws NoteNotFoundException {
        noteService.updateNote(note);
        return new RedirectView("/note/list");
    }

    @PostMapping(value = "/delete")
    public RedirectView deleteNote(@RequestParam UUID id) throws NoteNotFoundException {
        noteService.deleteById(id);
        return new RedirectView("/note/list");
    }
}
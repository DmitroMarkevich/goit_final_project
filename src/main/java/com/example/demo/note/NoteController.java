package com.example.demo.note;

import com.example.demo.exception.note.NoteNotFoundException;
import com.example.demo.markdown.HtmlService;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {
    private final NoteValidator noteValidator;
    private final NoteService noteService;
    private final UserService userService;
    private final HtmlService htmlService;

    @GetMapping("/list")
    public ModelAndView listNotes() {
        List<NoteDto> allNotes = noteService.getAllNotes();
        allNotes.forEach(note -> {
            String htmlContent = htmlService.markdownToHtml(note.getContent());
            note.setContent(htmlContent);
        });

        return new ModelAndView("note/list").addObject("allNotes", allNotes);
    }

    @GetMapping("/create")
    public ModelAndView getAddForm() {
        return new ModelAndView("note/create");
    }

    @PostMapping("/create")
    public ModelAndView createNote(@ModelAttribute @Valid NoteDto note, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        noteValidator.validate(note, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("error/base-error");
        } else {
            noteService.createNote(note);
            modelAndView.setViewName("redirect:/note/list");
        }

        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView showEditNoteForm(@RequestParam UUID id) throws NoteNotFoundException {
        return new ModelAndView("note/editor").addObject("editNote", noteService.getById(id));
    }

    @PostMapping("/edit")
    public ModelAndView saveNoteChanges(@ModelAttribute NoteDto note) throws NoteNotFoundException {
        noteService.updateNote(note);
        return new ModelAndView("redirect:/note/list");
    }

    @PostMapping("/delete")
    public ModelAndView deleteNote(@RequestParam UUID id) throws NoteNotFoundException {
        noteService.deleteById(id);
        return new ModelAndView("redirect:/note/list");
    }

    @GetMapping("/share")
    public ModelAndView showShareNoteForm(@RequestParam UUID id) throws NoteNotFoundException {
        NoteDto noteDto = noteService.getById(id);
        String htmlContent = htmlService.markdownToHtml(noteDto.getContent());

        if (noteService.canShare(noteDto)) {
            UserDto userDto = userService.getById(noteDto.getUserId());
            return new ModelAndView("note/share")
                    .addAllObjects(Map.of(
                            "title", noteDto.getTitle(),
                            "username", userDto.getUsername(),
                            "content", htmlContent,
                            "accessType", noteDto.getAccessType())
                    );
        }

        return new ModelAndView("error/404");
    }
}
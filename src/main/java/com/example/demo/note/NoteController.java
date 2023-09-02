package com.example.demo.note;

import com.example.demo.exception.note.NoteNotFoundException;
import com.example.demo.note.markdown.HtmlService;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final NoteService noteService;
    private final UserService userService;
    private final HtmlService htmlService;

    @GetMapping("/list")
    public ModelAndView listNotes(@RequestParam(name = "page", defaultValue = "1") Integer page) {
        Page<NoteDto> notePage = noteService.getNotesByPage(page, 2);
        List<NoteDto> allNotes = notePage.getContent();

        allNotes.forEach(note -> {
            String htmlContent = htmlService.markdownToHtml(note.getContent());
            note.setContent(htmlContent);
        });

        int allNotesSize = noteService.getAllNotes().size();
        int totalPages = notePage.getTotalPages();
        int nextPage = page < totalPages ? page + 1 : totalPages;
        int prevPage = page > 1 ? page - 1 : 1;

        return new ModelAndView("note/list")
                .addObject("allNotes", allNotes)
                .addObject("allNotesSize", allNotesSize)
                .addObject("currentPage", page)
                .addObject("totalPages", totalPages)
                .addObject("nextPage", nextPage)
                .addObject("prevPage", prevPage);
    }

    @GetMapping("/create")
    public ModelAndView getAddForm() {
        return new ModelAndView("note/create");
    }

    @PostMapping("/create")
    public ModelAndView createNote(@ModelAttribute @Valid NoteDto note, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

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
        return new ModelAndView("note/editor").addObject("editNote", noteService.getNoteById(id));
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
        NoteDto noteDto = noteService.getShareNote(id);

        String htmlContent = htmlService.markdownToHtml(noteDto.getContent());

        UserDto userDto = userService.getById(noteDto.getUserId());

        return new ModelAndView("note/share")
                .addAllObjects(Map.of(
                        "title", noteDto.getTitle(),
                        "username", userDto.getUsername(),
                        "content", htmlContent)
                );
    }
}
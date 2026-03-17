package com.yari.smartdoc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yari.smartdoc.dto.NoteDTO;
import com.yari.smartdoc.service.NoteService;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin("*")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // ✅ Create Note
    @PostMapping("/user/{userId}")
    public ResponseEntity<NoteDTO> createNote(
            @PathVariable Long userId,
            @RequestBody NoteDTO dto) {

        return ResponseEntity.ok(noteService.createNote(userId, dto));
    }

    // ✅ Get Notes of User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NoteDTO>> getUserNotes(
            @PathVariable Long userId) {

        return ResponseEntity.ok(noteService.getUserNotes(userId));
    }

    // ✅ Delete Note
    @DeleteMapping("/{noteId}/user/{userId}")
    public ResponseEntity<String> deleteNote(
            @PathVariable Long noteId,
            @PathVariable Long userId) {

        noteService.deleteNote(noteId, userId);
        return ResponseEntity.ok("Note deleted successfully");
    }
}
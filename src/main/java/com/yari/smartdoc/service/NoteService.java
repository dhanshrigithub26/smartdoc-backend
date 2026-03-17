package com.yari.smartdoc.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.yari.smartdoc.dto.NoteDTO;
import com.yari.smartdoc.entity.Note;
import com.yari.smartdoc.entity.User;
import com.yari.smartdoc.repository.NoteRepository;
import com.yari.smartdoc.repository.UserRepository;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository,
                       UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    // ✅ Create Note for User
    public NoteDTO createNote(Long userId, NoteDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setCreatedAt(LocalDateTime.now());
        note.setUser(user);

        Note saved = noteRepository.save(note);

        return new NoteDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getCreatedAt()
        );
    }

    // ✅ Get Notes of Specific User
    public List<NoteDTO> getUserNotes(Long userId) {

        return noteRepository.findByUserId(userId)
                .stream()
                .map(note -> new NoteDTO(
                        note.getId(),
                        note.getTitle(),
                        note.getContent(),
                        note.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    // ✅ Delete Note of User
    public void deleteNote(Long noteId, Long userId) {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not allowed to delete this note");
        }

        noteRepository.delete(note);
    }
}
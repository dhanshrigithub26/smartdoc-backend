package com.yari.smartdoc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.yari.smartdoc.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

    // 🔎 Fetch notes of specific user
    List<Note> findByUserId(Long userId);
}
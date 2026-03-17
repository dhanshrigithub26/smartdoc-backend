package com.yari.smartdoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yari.smartdoc.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByUserId(Long userId);
    
}
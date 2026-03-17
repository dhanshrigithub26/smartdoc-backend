package com.yari.smartdoc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.yari.smartdoc.dto.DocumentDTO;
import com.yari.smartdoc.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = {"http://localhost:4200", "https://smartdoc-frontend-x52x.vercel.app"})
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    // ================= UPLOAD DOCUMENT =================
    @PostMapping("/upload/{userId}")
    public ResponseEntity<DocumentDTO> upload(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) throws Exception {

        return ResponseEntity.ok(documentService.uploadDocument(userId, file));
    }

    // ================= GET USER DOCUMENTS =================
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DocumentDTO>> getUserDocs(
            @PathVariable Long userId) {

        return ResponseEntity.ok(documentService.getUserDocuments(userId));
    }

    // ================= DELETE DOCUMENT =================
    @DeleteMapping("/{docId}")
    public ResponseEntity<String> deleteDoc(@PathVariable Long docId) {

        documentService.deleteDocument(docId);
        return ResponseEntity.ok("Document deleted successfully");
    }
}
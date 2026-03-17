package com.yari.smartdoc.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yari.smartdoc.dto.DocumentDTO;
import com.yari.smartdoc.entity.Document;
import com.yari.smartdoc.entity.User;
import com.yari.smartdoc.repository.DocumentRepository;
import com.yari.smartdoc.repository.UserRepository;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;

    public DocumentService(DocumentRepository documentRepository,
                           UserRepository userRepository,
                           StorageService storageService) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.storageService = storageService;
    }

    // ✅ Upload Document
    public DocumentDTO uploadDocument(Long userId, MultipartFile file) throws Exception {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String fileUrl = storageService.uploadFile(file);

        Document doc = new Document();
        doc.setFileName(file.getOriginalFilename());
        doc.setFileUrl(fileUrl);
        doc.setFileSize(file.getSize());
        doc.setUploadedAt(LocalDateTime.now());
        doc.setUser(user);

        Document saved = documentRepository.save(doc);

        return new DocumentDTO(
                saved.getId(),
                saved.getFileName(),
                saved.getFileUrl(),
                saved.getFileSize(),
                saved.getUploadedAt()
        );
    }

    // ✅ Get User Documents
    public List<DocumentDTO> getUserDocuments(Long userId) {

        return documentRepository.findByUserId(userId)
                .stream()
                .map(doc -> new DocumentDTO(
                        doc.getId(),
                        doc.getFileName(),
                        doc.getFileUrl(),
                        doc.getFileSize(),
                        doc.getUploadedAt()
                ))
                .toList();
    }

    // ✅ Delete Document
    public void deleteDocument(Long docId) {
        documentRepository.deleteById(docId);
    }
}
package com.yari.smartdoc.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileUrl;

    private Long fileSize;

    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Document() {}

    // Getters & Setters

    public Long getId() { return id; }

    public String getFileName() { return fileName; }

    public String getFileUrl() { return fileUrl; }

    public Long getFileSize() { return fileSize; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }

    public User getUser() { return user; }

    public void setId(Long id) { this.id = id; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    public void setUser(User user) { this.user = user; }
}
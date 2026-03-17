package com.yari.smartdoc.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String uploadFile(MultipartFile file) throws Exception;

    void deleteFile(String fileName) throws Exception;
}
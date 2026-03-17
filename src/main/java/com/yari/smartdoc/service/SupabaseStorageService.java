package com.yari.smartdoc.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class SupabaseStorageService implements StorageService {

    private final String supabaseUrl;
    private final String supabaseKey;
    private final String bucket;

    private final WebClient webClient;

    public SupabaseStorageService(
            @Value("${supabase.url}") String supabaseUrl,
            @Value("${supabase.key}") String supabaseKey,
            @Value("${supabase.bucket}") String bucket) {

        this.supabaseUrl = supabaseUrl;
        this.supabaseKey = supabaseKey;
        this.bucket = bucket;

        this.webClient = WebClient.builder()
                .baseUrl(supabaseUrl + "/storage/v1")
                .defaultHeader("apikey", supabaseKey)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + supabaseKey)
                .build();
    }

    // ================= UPLOAD FILE =================
    @Override
    public String uploadFile(MultipartFile file) throws Exception {

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            webClient.put()
                    .uri("/object/" + bucket + "/" + fileName)
                    .header("x-upsert", "true")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .bodyValue(file.getBytes())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return supabaseUrl + "/storage/v1/object/public/" + bucket + "/" + fileName;

        } catch (WebClientResponseException e) {
            throw new RuntimeException("Supabase upload failed: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    // ================= DELETE FILE =================
    @Override
    public void deleteFile(String fileName) throws Exception {

        try {
            webClient.delete()
                    .uri("/object/" + bucket + "/" + fileName)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

        } catch (WebClientResponseException e) {
            throw new RuntimeException("Supabase delete failed: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("File delete failed: " + e.getMessage());
        }
    }
}
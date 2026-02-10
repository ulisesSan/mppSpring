package com.telcel.mpp.clients;

import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MppDbClient {
        private final RestClient restClient;

        public MppDbClient() {
        // Configuramos la URL base de la "otra" API
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
        }
        @Async
        public void uploadToFTP(String pathZip,String originalName) {
                File file = new File(pathZip);
        
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("file", new FileSystemResource(file));

                log.info("Enviando ZIP al destino final...");

                try {
                        String response = restClient.post()
                        .uri("/uploadtoftp")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(body)
                        .retrieve()
                        .body(String.class);

                        log.info("Respuesta de la otra API: " + response);
            
                        if (file.delete()) log.info("ZIP temporal eliminado.");

                } catch (Exception e) {
                        log.error("Error al conectar con la otra API: " + e.getMessage());
                }
        }
}

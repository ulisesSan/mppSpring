package com.telcel.mpp.service;

import com.telcel.mpp.models.MppModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@org.springframework.stereotype.Service
public interface Service {

    ResponseEntity<MppModel> uploadDocument(MultipartFile inputStream);
}

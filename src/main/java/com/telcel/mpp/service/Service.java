package com.telcel.mpp.service;

import com.telcel.mpp.models.MppModel;
import com.telcel.mpp.models.MppModel2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public interface Service {

    ResponseEntity<List<MppModel2>> uploadDocument(MultipartFile inputStream);
}

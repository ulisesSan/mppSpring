package com.telcel.mpp.service;
import com.telcel.mpp.models.MppModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface MppService {

    ResponseEntity<List<MppModel>> uploadDocument(MultipartFile inputStream);
}

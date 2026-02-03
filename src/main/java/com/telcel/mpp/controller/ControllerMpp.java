package com.telcel.mpp.controller;

import com.telcel.mpp.models.MppModel2;
import com.telcel.mpp.service.impl.MppImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/")
public class ControllerMpp {
    private final MppImpl mppImpl = new MppImpl();

    @PostMapping("/upload")
    public ResponseEntity<List<MppModel2>> uploadDocument(@RequestParam("file") MultipartFile file){
        return mppImpl.uploadDocument(file);
    }
}

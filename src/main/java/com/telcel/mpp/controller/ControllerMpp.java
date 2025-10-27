package com.telcel.mpp.controller;

import com.telcel.mpp.models.MppModel;
import com.telcel.mpp.service.impl.MppImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class ControllerMpp {
    private MppImpl mppImpl = new MppImpl();

    @PostMapping("/upload")
    public ResponseEntity<MppModel> uploadDocument(@RequestParam("file") MultipartFile file){
        return mppImpl.uploadDocument(file);
    }
}

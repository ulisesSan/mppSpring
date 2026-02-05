package com.telcel.mpp.controller;

import com.telcel.mpp.models.MppModel;
import com.telcel.mpp.mppOperations.lmStudio;
import com.telcel.mpp.service.impl.MppImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/")
public class ControllerMpp {
    private final MppImpl mppImpl = new MppImpl();

    @PostMapping("/upload")
    public ResponseEntity<List<MppModel>> uploadDocument(@RequestParam("file") MultipartFile file){
        return mppImpl.uploadDocument(file);
    }

    @PostMapping("/getiaresponse")
    public ResponseEntity<String> getIAResponse(@RequestParam("file")MultipartFile file){
        lmStudio lmStudios = new lmStudio();
        String respuesta = lmStudios.gemmaResponse(mppImpl.uploadDocument(file).getBody());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}

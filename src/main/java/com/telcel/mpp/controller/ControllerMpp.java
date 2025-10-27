package com.telcel.mpp.controller;

import com.telcel.mpp.service.impl.MppImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class ControllerMpp {
    private MppImpl mppImpl = new MppImpl();

    @GetMapping(value ="hola")
    public String saludo(){
        mppImpl.ReadMpp();
        return "Hola";
    }

    @PostMapping("/upload")
    public void uploadDocument(@RequestParam("file") MultipartFile file){
        try{
            mppImpl.uploadDocument(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

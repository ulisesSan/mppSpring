package com.telcel.mpp.service.impl;

import com.telcel.mpp.models.MppModel;
import com.telcel.mpp.mppOperatiosn.MppOperations;
import com.telcel.mpp.service.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.mpxj.*;
import org.mpxj.mpp.MPPReader;
import org.mpxj.mpx.MPXWriter;
import org.mpxj.reader.ProjectReader;
import org.mpxj.reader.UniversalProjectReader;
import org.mpxj.writer.ProjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.UUID;

@Slf4j
public class MppImpl implements Service {
    private MppOperations mppOperations = new MppOperations();
    private final String UPLOAD_DIR = "../mppSpring";
    private  MppModel mppModel = new MppModel();


    @Autowired
    public ResponseEntity<MppModel> uploadDocument(MultipartFile inputStream){
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream.getInputStream());
            Path path = Paths.get(UPLOAD_DIR + "/" + inputStream.getName()+".mpp");
            Files.write(path, bytes);
            mppModel = MppOperations.ReadMpp();
            Files.delete(path);
            return new ResponseEntity<>(mppModel, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Hubo un error al subir archivo "+ e);
            return new ResponseEntity<>(mppModel, HttpStatus.BAD_REQUEST);
        }
    }
}

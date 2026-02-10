package com.telcel.mpp.service.impl;

import com.telcel.mpp.models.MppModel;
import com.telcel.mpp.models.ProjectModelResponse;
import com.telcel.mpp.mppOperations.MppOperations;
import com.telcel.mpp.models.MppModel;
import com.telcel.mpp.service.MppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import net.sf.mpxj.reader.UniversalProjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;


@Slf4j

public class MppImpl implements MppService {
    private final MppOperations mppOperations = new MppOperations();
    private ProjectModelResponse projectModelResponse = new ProjectModelResponse();
    

    @Autowired
    public ResponseEntity<ProjectModelResponse> uploadDocument(MultipartFile inputStream){
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream.getInputStream());
            String UPLOAD_DIR = "../mppSpring";
            Path path = Paths.get(UPLOAD_DIR + "/" + inputStream.getName()+".mpp");
            Files.write(path, bytes);
            projectModelResponse = MppOperations.ReadMpp();
            Files.delete(path);
            return new ResponseEntity<>(projectModelResponse, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Hubo un error al subir archivo "+ e);
            return new ResponseEntity<>(projectModelResponse, HttpStatus.BAD_REQUEST);
        }
    }
}

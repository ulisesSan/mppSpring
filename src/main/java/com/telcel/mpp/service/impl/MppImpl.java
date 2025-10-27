package com.telcel.mpp.service.impl;

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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.UUID;

@Slf4j
public class MppImpl implements Service {
    ProjectWriter writer;
    MPPReader reader;
    ProjectFile projectFile;

    private final String UPLOAD_DIR = "../mppSpring";

    public void ReadMpp(){

        try{
            writer = new MPXWriter();
            reader = new MPPReader();
            reader.setReadPresentationData(true);
            projectFile = reader.read("/home/ulises/Descargas/" +
                    "579399_ REDIRECCIONAMIENTO DE LA URL EN EL COMPONENTE PAGOSHOY.mpp");



            writer.write(projectFile,"/home/ulises/Descargas/de_mpx.mpx");
            //projectFile.setBaseline(projectFile, 12);

//            log.info(projectFile.getResources().toString());
//            //log.info(projectFile.getBaseline().toString());
//            log.info(projectFile.getActivityCodes().toString());
//            log.info(projectFile.getBaselineCalendar().toString());
//            log.info(projectFile.getCalendars().toString());
//            log.info(projectFile.getChildTasks().toString());
//            log.info(projectFile.getBaselineCalendar().getName().toString());
//            log.info(projectFile.getTables().toString());
//            log.info(projectFile.getViews().toString());
//            //log.info(projectFile.getProjectProperties().getName().toString());
//            log.info(projectFile.getProjectProperties().toString());
//            log.info(projectFile.getProjectProperties().getWork().toString());
////            for (Resource resource : projectFile.getResources())
////            {
////                System.out.println("Resource: " + resource.getName()
////                        + " (Unique ID=" + resource.getUniqueID() + ")");
////            }
            ProjectProperties properties = projectFile.getProjectProperties();
            log.info(properties.getWork().toString());

            log.info(projectFile.getProjectProperties().getCompany()); /// Nombre de la Compañia
            log.info(projectFile.getProjectProperties().getStartDate().toString()); //// fecha de inicio
            log.info(projectFile.getProjectProperties().getFinishDate().toString()); //// fecha de final
            log.info(projectFile.getProjectProperties().getStatusDate().toString());/// devuelve la fecha del status
            log.info(projectFile.getChildTasks().toString());/// devuelve la tarea hija
            log.info(projectFile.getTasks().toString());


            //log.info( projectFile.getProjectProperties().toString() );
        }catch(MPXJException | IOException e){
            log.error("Error: {}", e);
        }
    }

    @Autowired
    public void uploadDocument(InputStream inputStream){
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            Path path = Paths.get(UPLOAD_DIR + "/" + UUID.randomUUID()+".mpp");
            Files.write(path, bytes);
        } catch (IOException e) {
            log.error("Hubo un error al subir archivo "+ e);
        }
    }
}

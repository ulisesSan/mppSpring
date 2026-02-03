package com.telcel.mpp.service.impl;

import com.telcel.mpp.models.MppModel;
import com.telcel.mpp.models.MppModel2;
import com.telcel.mpp.mppOperatiosn.MppOperations;
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
    private  MppModel mppModel = new MppModel();
    //private MppModel2 model2 = new MppModel2();
    private List<MppModel2> res = new ArrayList<>();

    @Autowired
    public ResponseEntity<List<MppModel2>> uploadDocument(MultipartFile inputStream){
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream.getInputStream());
            String UPLOAD_DIR = "../mppSpring";
            Path path = Paths.get(UPLOAD_DIR + "/" + inputStream.getName()+".mpp");
            Files.write(path, bytes);
            mppModel = MppOperations.ReadMpp();
            Files.delete(path);
            res = prueba();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Hubo un error al subir archivo "+ e);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }



    }

    private List<MppModel2> prueba(){
        List<MppModel2> listMpp = new ArrayList<>();
        try {
            // El UniversalProjectReader detecta automáticamente que es un .mpp
            UniversalProjectReader reader = new UniversalProjectReader();
            ProjectFile project = reader.read("/home/ulises/Descargas/" +
                    "581179_ACTUALIZACION DE CLIENTES BUS EN LOS DAT DE SISTEMA EN ACE.mpp");

            log.info("Proyecto: " + project.getProjectProperties().getProjectTitle());
            // Recorrer todas las tareas
            for (Task task : project.getTasks()) {
                MppModel2 model2 = new MppModel2();
                // Saltamos la tarea "0" que es el resumen del proyecto
                if (task.getID() == 0) continue;


                model2.setTaskName(task.getName());
                model2.setStartDate(String.valueOf(task.getStart()));
                model2.setEndDate(String.valueOf(task.getFinish()));
                model2.setDuration(task.getDuration().toString());
                model2.setPercentageComplete(task.getPercentageComplete().toString());

                log.info("-----------------------------------");
                log.info("Tarea: " + task.getName());
                log.info("Inicio: " + task.getStart());
                log.info("Fin: " + task.getFinish());
                log.info("Duración: " + task.getDuration());
                log.info("% Completado: " + task.getPercentageComplete() + "%");

                // Ejemplo de cómo ver predecesoras
                if (!task.getPredecessors().isEmpty()) {
                    log.info("Tiene " + task.getPredecessors().size() + " predecesoras.");
                    model2.setPredecessor("La actividad tiene como predecesor " + task.getPredecessors().size()+
                            " actividad");
                    model2.setPredecessors(task.getPredecessors().get(0).toString());

                }

                listMpp.add(model2);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMpp;
    }
}

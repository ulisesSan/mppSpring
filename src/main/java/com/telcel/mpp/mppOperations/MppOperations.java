package com.telcel.mpp.mppOperations;

import com.telcel.mpp.models.MppModel;
import com.telcel.mpp.models.ProjectModelResponse;
import com.telcel.mpp.models.MppModel;

import lombok.extern.slf4j.Slf4j;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ProjectProperties;
import net.sf.mpxj.Task;
import net.sf.mpxj.TaskContainer;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXWriter;
import net.sf.mpxj.reader.UniversalProjectReader;
import net.sf.mpxj.writer.ProjectWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;

import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mspdi.MSPDIWriter;

@Slf4j
public class MppOperations {

    private static ProjectModelResponse projectModelResponse = new ProjectModelResponse();

    public static ProjectModelResponse ReadMpp(){

        List<MppModel> listMpp = new ArrayList<>();
        double avanceTareas = 0;
        try {
            
            UniversalProjectReader reader = new UniversalProjectReader();
            ProjectFile project = reader.read("../mppSpring/file.mpp");

            log.info("Proyecto: " + project.getProjectProperties().getProjectTitle());

            
            
            for (Task task : project.getTasks()) {


                MppModel model = new MppModel();
                
                if (task.getID() == 0) continue;


                model.setTaskName(task.getName());
                model.setStartDate(String.valueOf(task.getStart()));
                model.setEndDate(String.valueOf(task.getFinish()));
                model.setDuration(task.getDuration().toString());
                model.setPercentageComplete(task.getPercentageComplete().toString());
                model.setHierarchyLevel(task.getOutlineLevel());

                if (!task.getPredecessors().isEmpty()) {
                    model.setPredecessor("La actividad tiene como predecesor " + task.getPredecessors().size()+
                            " actividad");
                    model.setPredecessors(task.getPredecessors().get(0).toString());

                }

                listMpp.add(model);
                avanceTareas = task.getPercentageComplete().doubleValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        projectModelResponse.setPersentageComplete(avanceTareas);
        projectModelResponse.setMpp(listMpp);
        
        return projectModelResponse;
    }


    public static void modificarProyecto(String rutaArchivo) {
        // 1. Leer el archivo original
        try{
        MPPReader reader = new MPPReader();
        ProjectFile project = reader.read(new File(rutaArchivo));

        // 2. Buscar una tarea específica (por ID o nombre)
        Task task = project.getTaskByID(3);
        if (task != null) {
         // Modificar propiedades
            task.setName("Tarea Modificada por Ulises");
            task.setPercentageComplete(50.0); // Actualizar progreso
        }

        // 3. ¡IMPORTANTE! Guardar como XML (MS Project lo lee nativo)
        // Guardar como .mpp directamente tiene muchas restricciones de escritura
        MSPDIWriter writer = new MSPDIWriter();
        writer.write(project, "../mppSpring/proyecto_actualizado.xml");

        String rutaCarpeta = "../mppSpring/ProyectosModificados/"; // Asegúrate de que la carpeta exista
        String nombreArchivo = "proyecto_version_1.mpx";

        // En lugar de ByteArrayOutputStream, usamos FileOutputStream
        try (FileOutputStream fos = new FileOutputStream(rutaCarpeta + nombreArchivo)) {
            MSPDIWriter writerr = new MSPDIWriter();
            writerr.write(project, fos); // Aquí se escribe físicamente en el disco C:
        }
    }catch(Exception e){
        log.atError();
    }
    }
}

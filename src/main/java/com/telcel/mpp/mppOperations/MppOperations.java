package com.telcel.mpp.mppOperations;

import com.telcel.mpp.models.MppModel;
import com.telcel.mpp.models.MppModel;

import lombok.extern.slf4j.Slf4j;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ProjectProperties;
import net.sf.mpxj.Task;
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


    public static List<MppModel> ReadMpp(){

        List<MppModel> listMpp = new ArrayList<>();
        try {
            // El UniversalProjectReader detecta automáticamente que es un .mpp
            UniversalProjectReader reader = new UniversalProjectReader();
            ProjectFile project = reader.read("../mppSpring/file.mpp");

            log.info("Proyecto: " + project.getProjectProperties().getProjectTitle());
            // Recorrer todas las tareas
            for (Task task : project.getTasks()) {


                MppModel model = new MppModel();
                // Saltamos la tarea "0" que es el resumen del proyecto
                if (task.getID() == 0) continue;


                model.setTaskName(task.getName());
                model.setStartDate(String.valueOf(task.getStart()));
                model.setEndDate(String.valueOf(task.getFinish()));
                model.setDuration(task.getDuration().toString());
                model.setPercentageComplete(task.getPercentageComplete().toString());
                model.setHierarchyLevel(task.getOutlineLevel());



                
                //log.info("-----------------------------------");
                //log.info("Tarea: " + task.getName());
                //log.info("Inicio: " + task.getStart());
                //log.info("Fin: " + task.getFinish());
                //log.info("Duración: " + task.getDuration());
                //log.info("% Completado: " + task.getPercentageComplete() + "%");

                // Ejemplo de cómo ver predecesoras
                if (!task.getPredecessors().isEmpty()) {
                    log.info("Tiene " + task.getPredecessors().size() + " predecesoras.");
                    model.setPredecessor("La actividad tiene como predecesor " + task.getPredecessors().size()+
                            " actividad");
                    model.setPredecessors(task.getPredecessors().get(0).toString());

                }

                //modificarProyecto("../mppSpring/file.mpp");
                listMpp.add(model);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMpp;
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

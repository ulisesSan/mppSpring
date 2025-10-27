package com.telcel.mpp.mppOperatiosn;

import com.telcel.mpp.models.MppModel;
import lombok.extern.slf4j.Slf4j;
import org.mpxj.MPXJException;
import org.mpxj.ProjectFile;
import org.mpxj.ProjectProperties;
import org.mpxj.mpp.MPPReader;
import org.mpxj.mpx.MPXWriter;
import org.mpxj.writer.ProjectWriter;

import java.io.IOException;
@Slf4j
public class MppOperations {

    private static ProjectWriter writer;
    private static MPPReader reader;
    private static ProjectFile projectFile;
    private static MppModel mppModel;

    public static MppModel ReadMpp(){

        try{
            writer = new MPXWriter();
            reader = new MPPReader();
            reader.setReadPresentationData(true);
            projectFile = reader.read("../mppSpring/file.mpp");



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
            log.info(projectFile.getTasks().get(1).getName());

            mppModel = new MppModel();

            mppModel.setCompanyName(projectFile.getProjectProperties().getCompany());
            mppModel.setStartDate(projectFile.getProjectProperties().getStartDate().toString());
            mppModel.setFinishDate(projectFile.getProjectProperties().getFinishDate().toString());
            mppModel.setNameFile(projectFile.getTasks().get(1).getName());

            //log.info( projectFile.getProjectProperties().toString() );
        }catch(MPXJException | IOException e){
            log.error("Error: {}", e);
        }
        return mppModel;
    }
}

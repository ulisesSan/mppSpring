package com.telcel.mpp.mppOperatiosn;

import com.telcel.mpp.models.MppModel;
import lombok.extern.slf4j.Slf4j;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ProjectProperties;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXWriter;
import net.sf.mpxj.writer.ProjectWriter;

import java.io.IOException;
@Slf4j
public class MppOperations {

    private static MppModel mppModel;

    public static MppModel ReadMpp(){

        try{
            ProjectWriter writer = new MPXWriter();
            MPPReader reader = new MPPReader();
            reader.setReadPresentationData(true);
            ProjectFile projectFile = reader.read("../mppSpring/file.mpp");



            writer.write(projectFile,"/home/ulises/Descargas/de_mpx.mpx");
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
            log.error("Error: ", e);
        }
        return mppModel;
    }
}

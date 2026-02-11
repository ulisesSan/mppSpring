package com.telcel.mpp.zip;

import java.io.*;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.scheduling.annotation.Async;

import com.telcel.mpp.clients.FtpGetDBData;
import com.telcel.mpp.clients.MppDbClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Async
public class ZipOperations {

        private static MppDbClient mppDbClient = new MppDbClient();
        private static FtpGetDBData ftpGetDBData = new FtpGetDBData();

        public static void ZipCompress(String newName) {

                UUID uuid = UUID.randomUUID();
                String archivoZip = uuid + ".zip";

                String nameCleaned = newName.trim().replaceAll("[^a-zA-Z0-9-_\\.]", "_");
                if (ftpGetDBData.obtenerInfoPorNombre(nameCleaned) != null) {
                        log.info("Documento ya existe en ftp");

                } else {
                        File originalFile = new File("../mppSpring/file.mpp");
                        File renamedFile = new File("../mppSpring/" + nameCleaned + ".mpp");

                        if (originalFile.renameTo(renamedFile))
                                log.info("Archivo renombrado a: " + nameCleaned);
                        else {
                                log.info("Error al renombrar archivo; " + nameCleaned);
                                return;
                        }

                        try (FileOutputStream fos = new FileOutputStream(archivoZip);
                                        ZipOutputStream zos = new ZipOutputStream(fos);
                                        FileInputStream fis = new FileInputStream(renamedFile)) {

                                ZipEntry zipEntry = new ZipEntry(new File(renamedFile.toURI()).getName());
                                zos.putNextEntry(zipEntry);

                                byte[] buffer = new byte[1024];
                                int bytesLeidos;
                                while ((bytesLeidos = fis.read(buffer)) > 0) {
                                        zos.write(buffer, 0, bytesLeidos);
                                }

                                zos.closeEntry();
                                log.info("Archivo comprimido exitosamente en: " + archivoZip);

                                mppDbClient.uploadToFTP(uuid + ".zip", nameCleaned, uuid);

                        } catch (IOException e) {
                                log.info("Error al comprimir el archivo: " + e.getMessage());
                                throw new RuntimeException(e);
                        } finally {
                                renamedFile.delete();
                                File deleteZip = new File("../mppSpring/" + uuid + ".zip");
                                File deleteZip2 = new File("./mppSpring/file.mpp");
                                deleteZip.delete();
                        }
                }

        }
}

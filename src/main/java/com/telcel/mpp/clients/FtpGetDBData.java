package com.telcel.mpp.clients;

import org.springframework.web.client.RestTemplate;

import com.telcel.mpp.models.FtpDatabase;

public class FtpGetDBData {
        private RestTemplate restTemplate = new RestTemplate();
        private final String BASE_URL = "http://localhost:8082";

        public FtpGetDBData() {
                this.restTemplate = new RestTemplate();
        }

        public FtpDatabase obtenerInfoPorNombre(String docName) {
                // La URL final será: http://localhost:8082/getuuid/archivo.mpp
                String url = BASE_URL + "/getuuid/" + docName;

                try {
                        return restTemplate.getForObject(url, FtpDatabase.class);
                } catch (Exception e) {
                        System.err.println("Error al conectar con la API: " + e.getMessage());
                        return null;
                }
        }
}

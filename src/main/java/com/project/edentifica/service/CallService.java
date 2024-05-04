package com.project.edentifica.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CallService {
    private final String SERVER_CALLS_URL = "http://whatsapp.api.applerta.com/messages";

    private final RestTemplate restTemplate;

    public CallService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendCall(String text, int copies, String audioLanguage, long userId, String phone) {
        String url = SERVER_CALLS_URL;

        String requestBody = String.format(
                "{\"text\": \"%s\", \"copies\": %d, \"audioLanguage\": \"%s\", \"userId\": %d, \"phone\": \"%s\"}",
                text, copies, audioLanguage, userId, phone
        );

        // Configurar el encabezado de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        restTemplate.put(url, request);
    }
}

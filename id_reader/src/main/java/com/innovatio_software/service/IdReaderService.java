package com.innovatio_software.service;
import com.innovatio_software.client.azureDocumentClient;
import com.innovatio_software.document.DocumentoRequest;
import com.innovatio_software.excepcion.DocumentProcessingException;
import com.innovatio_software.model.IdReaderModel;
import com.innovatio_software.repository.IdReaderRepository;

import jakarta.transaction.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class IdReaderService {

    private final azureDocumentClient azureDocumentClient;
    private final IdReaderRepository idReaderRepository;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Para manejar JSON

    @Transactional
    public IdReaderModel processDocument(String base64Source) {
        try {
            DocumentoRequest request = new DocumentoRequest(base64Source);

            ResponseEntity<String> response = azureDocumentClient.analyzeDocument(request);
            if (response.getStatusCode() != HttpStatus.ACCEPTED) {
                throw new DocumentProcessingException("Error: Azure no aceptó el documento.");
            }

            String operationLocation = response.getHeaders().getFirst("Operation-Location");
            if (operationLocation == null) {
                throw new DocumentProcessingException("Azure no devolvió una URL de seguimiento.");
            }

            String jsonResponse = getProcessingResult(operationLocation);
            IdReaderModel data = parseResponse(jsonResponse);

            System.out.println("Resultado de la extracción: " + jsonResponse);
            System.out.println("nombre extraída: " + data.getFirstName());
            System.out.println("apellidos extraída: " + data.getLastName());
            System.out.println("Fecha de nacimiento extraída: " + data.getDateOfBirth());
            System.out.println("documento extraída: " + data.getDocumentNumber());
            System.out.println("Lugar de nacimiento extraída: " + data.getLugarDeNacimiento());
            return idReaderRepository.save(data);

        } catch (Exception e) {
            throw new DocumentProcessingException("Error procesando el documento", e);
        }
    }

    private String getProcessingResult(String operationUrl) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Ocp-Apim-Subscription-Key", "4xHwDz6EfEqcPXVAbKyhM549Hgq7kkYvRuel83CR86dPHR8XtUO9JQQJ99BCACYeBjFXJ3w3AAALACOGiOHU");
        HttpEntity<Void> request = new HttpEntity<>(headers);

        int retryCount = 0;
        int maxRetries = 10;
        int baseDelay = 2000; 

        while (retryCount < maxRetries) {
            ResponseEntity<String> response = restTemplate.exchange(operationUrl, HttpMethod.GET, request, String.class);
            if (response.getBody().contains("\"status\":\"succeeded\"")) {
                return response.getBody();
            }
            int delay = (int) (baseDelay * Math.pow(2, retryCount));
            Thread.sleep(Math.min(delay, 10000));
            retryCount++;
        }

        throw new DocumentProcessingException("El procesamiento del documento tomó demasiado tiempo.");
    }

    private IdReaderModel parseResponse(String responseBody) throws Exception {
        JsonNode root = objectMapper.readTree(responseBody);
        System.out.println("Estructura del JSON parseado: " + root.toPrettyString() + "finalizado");

        JsonNode documentFields = root.path("analyzeResult").path("documents").get(0).path("fields"); // Accede a los campos correctamente

        IdReaderModel data = new IdReaderModel();
        data.setDocumentNumber(obtenerValor(documentFields, "numero_documento"));
        data.setLastName(obtenerValor(documentFields, "apellidos"));
        data.setFirstName(obtenerValor(documentFields, "nombres"));
        data.setDateOfBirth(obtenerValor(documentFields, "fecha_de_nacimiento"));
        data.setLugarDeNacimiento(obtenerValor(documentFields, "lugar_de_nacimiento"));
        data.setEstatura(obtenerValor(documentFields, "estatura"));
        data.setRh(obtenerValor(documentFields, "rh"));
        data.setSexo(obtenerValor(documentFields, "sexo"));
        data.setFechaDeExpedicion(obtenerValor(documentFields, "fecha_de_expedicion"));
        data.setLugarDeExpedicion(obtenerValor(documentFields, "lugar_de_expedicion"));
        return data;
    }

    private String obtenerValor(JsonNode node, String campo) {
        //return node.has(campo) && node.get(campo).has("valueString") ? node.get(campo).get("valueString").asText() : "";

        if (node.has(campo)) {
            JsonNode campoNode = node.get(campo);
    
            // Si el campo tiene valueString, devolverlo directamente
            if (campoNode.has("valueString")) {
                return campoNode.get("valueString").asText();
            }
    
            // Si el campo tiene valueDateTime (formato de fecha en JSON)
            if (campoNode.has("valueDate")) {
                String fechaISO = campoNode.get("valueDate").asText();
                return convertirFechaISOaString(fechaISO);
            }
    
            // Si el campo tiene un número (posible timestamp en milisegundos)
            if (campoNode.has("valueNumber")) {
                long timestamp = campoNode.get("valueNumber").asLong();
                return convertirTimestampAString(timestamp);
            }
        }
        return "";
    }

    private String convertirFechaISOaString(String fechaISO) {
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date fecha = isoFormat.parse(fechaISO);
            return formatearFecha(fecha);
        } catch (Exception e) {
            return fechaISO; // Si hay un error, devuelve la fecha original
        }
    }

    private String convertirTimestampAString(long timestamp) {
        Date fecha = new Date(timestamp);
        return formatearFecha(fecha);
    }

    private String formatearFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return formato.format(fecha);
    }
}
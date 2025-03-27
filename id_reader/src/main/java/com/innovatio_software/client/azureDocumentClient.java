package com.innovatio_software.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.innovatio_software.document.DocumentoRequest;

@FeignClient(name = "azureClient", url = "https://prueba-innovatio-software-demo.cognitiveservices.azure.com")
public interface azureDocumentClient {
    
    @PostMapping(value = "/documentintelligence/documentModels/cedula-text-extractor:analyze?api-version=2024-11-30", 
                 headers = "Ocp-Apim-Subscription-Key=4xHwDz6EfEqcPXVAbKyhM549Hgq7kkYvRuel83CR86dPHR8XtUO9JQQJ99BCACYeBjFXJ3w3AAALACOGiOHU",
                 consumes = "application/json")
    ResponseEntity<String> analyzeDocument(@RequestBody DocumentoRequest request);
}

package com.innovatio_software.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innovatio_software.document.DocumentoRequest;
import com.innovatio_software.model.IdReaderModel;
import com.innovatio_software.service.IdReaderService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class IdReaderController {
    

    private final IdReaderService idReaderService;

    @PostMapping("/upload")
    public ResponseEntity<IdReaderModel> uploadDocument(@RequestBody DocumentoRequest request) {
        if (request.getBase64Source() == null || request.getBase64Source().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        IdReaderModel processedData = idReaderService.processDocument(request.getBase64Source());
        return ResponseEntity.ok(processedData);
    }
}

package com.medical.controller;

import com.medical.dto.DocumentSummaryResponse;
import com.medical.service.PdfSummarizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final PdfSummarizationService pdfSummarizationService;

    @PostMapping("/summarize")
    public ResponseEntity<DocumentSummaryResponse> summarizeDocument(
            @RequestParam("file") MultipartFile file) {
        validatePdfFile(file);
        return ResponseEntity.ok(pdfSummarizationService.summarizeDocument(file));
    }

    @PostMapping("/summarize/brief")
    public ResponseEntity<DocumentSummaryResponse> summarizeDocumentBrief(
            @RequestParam("file") MultipartFile file) {
        validatePdfFile(file);
        return ResponseEntity.ok(pdfSummarizationService.summarizeDocumentBrief(file));
    }

    private void validatePdfFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Uploaded file is empty");
        }
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.toLowerCase().endsWith(".pdf")) {
            throw new RuntimeException("Only PDF files are accepted");
        }
    }
}

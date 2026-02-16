package com.medical.service;

import com.medical.dto.DocumentSummaryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfSummarizationService {

    private final ChatClient.Builder chatClientBuilder;
    private final PromptLibraryService promptLibraryService;

    public DocumentSummaryResponse summarizeDocument(MultipartFile file) {
        log.info("Processing PDF document: {}", file.getOriginalFilename());

        try {
            InputStreamResource resource = new InputStreamResource(file.getInputStream());
            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(resource);
            List<Document> documents = pdfReader.get();

            log.info("Extracted {} pages from PDF", documents.size());

            String documentContent = documents.stream()
                    .map(Document::getContent)
                    .collect(Collectors.joining("\n\n--- Page Break ---\n\n"));

            if (documentContent.length() > 15000) {
                log.warn("Document content truncated from {} to 15000 chars", documentContent.length());
                documentContent = documentContent.substring(0, 15000) + "\n\n[Content truncated for processing...]";
            }

            Map<String, String> params = new HashMap<>();
            params.put("document_content", documentContent);
            String resolvedPrompt = promptLibraryService.resolveTemplate("document-summary", params);

            ChatClient chatClient = chatClientBuilder.build();
            String summary = chatClient.prompt()
                    .user(resolvedPrompt)
                    .call()
                    .content();

            return DocumentSummaryResponse.builder()
                    .fileName(file.getOriginalFilename())
                    .summary(summary)
                    .totalPages(documents.size())
                    .templateUsed("document-summary")
                    .build();

        } catch (Exception e) {
            log.error("Error processing PDF: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process PDF document: " + e.getMessage(), e);
        }
    }

    public DocumentSummaryResponse summarizeDocumentBrief(MultipartFile file) {
        log.info("Processing PDF document (brief): {}", file.getOriginalFilename());

        try {
            InputStreamResource resource = new InputStreamResource(file.getInputStream());
            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(resource);
            List<Document> documents = pdfReader.get();

            String documentContent = documents.stream()
                    .map(Document::getContent)
                    .collect(Collectors.joining("\n\n"));

            if (documentContent.length() > 10000) {
                documentContent = documentContent.substring(0, 10000) + "\n\n[Content truncated...]";
            }

            Map<String, String> params = new HashMap<>();
            params.put("document_content", documentContent);
            String resolvedPrompt = promptLibraryService.resolveTemplate("document-summary-brief", params);

            ChatClient chatClient = chatClientBuilder.build();
            String summary = chatClient.prompt()
                    .user(resolvedPrompt)
                    .call()
                    .content();

            return DocumentSummaryResponse.builder()
                    .fileName(file.getOriginalFilename())
                    .summary(summary)
                    .totalPages(documents.size())
                    .templateUsed("document-summary-brief")
                    .build();

        } catch (Exception e) {
            log.error("Error processing PDF: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process PDF document: " + e.getMessage(), e);
        }
    }
}

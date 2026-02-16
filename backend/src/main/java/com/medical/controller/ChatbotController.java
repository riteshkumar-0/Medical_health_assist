package com.medical.controller;

import com.medical.dto.ChatbotRequest;
import com.medical.dto.ChatbotResponse;
import com.medical.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatbotController {

    private final ChatbotService chatbotService;

    @PostMapping("/chat")
    public ResponseEntity<ChatbotResponse> chat(@RequestBody ChatbotRequest request) {
        ChatbotResponse response = chatbotService.chat(request);
        return ResponseEntity.ok(response);
    }
}

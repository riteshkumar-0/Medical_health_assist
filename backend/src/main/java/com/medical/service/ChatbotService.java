package com.medical.service;

import com.medical.dto.ChatbotRequest;
import com.medical.dto.ChatbotResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatbotService {

    private final ChatClient.Builder chatClientBuilder;
    private final PromptLibraryService promptLibraryService;

    private final Map<String, List<String>> conversationHistory = new ConcurrentHashMap<>();

    public ChatbotResponse chat(ChatbotRequest request) {
        String sessionId = request.getSessionId() != null ? request.getSessionId() : UUID.randomUUID().toString();
        String userMessage = request.getMessage();

        log.info("Chatbot request [session={}]: {}", sessionId, userMessage);

        List<String> history = conversationHistory.computeIfAbsent(sessionId, k -> new ArrayList<>());
        StringBuilder historyBuilder = new StringBuilder();
        for (String entry : history) {
            historyBuilder.append(entry).append("\n");
        }

        Map<String, String> params = new HashMap<>();
        params.put("user_message", userMessage);
        params.put("conversation_history",
                historyBuilder.length() > 0 ? historyBuilder.toString() : "No previous conversation.");

        String resolvedPrompt = promptLibraryService.resolveTemplate("chatbot", params);

        ChatClient chatClient = chatClientBuilder.build();
        String reply = chatClient.prompt()
                .user(resolvedPrompt)
                .call()
                .content();

        history.add("User: " + userMessage);
        history.add("Assistant: " + reply);
        if (history.size() > 20) {
            history.subList(0, history.size() - 20).clear();
        }

        log.info("Chatbot response generated [session={}]", sessionId);

        return ChatbotResponse.builder()
                .reply(reply)
                .sessionId(sessionId)
                .build();
    }
}

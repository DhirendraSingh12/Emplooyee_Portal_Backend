package com.example.th.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.th.model.ChatRequest;
import com.example.th.service.DynamicChatbotService;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    @Autowired
    private DynamicChatbotService chatbotService;

    // Initial greeting when the chatbot is first clicked
    @GetMapping("/greet")
    public String greet() {
        return chatbotService.greetEmployee();
    }

    // Handle the query and employeeId as input from request body
    @PostMapping("/query")
    public String handleQuery(@RequestBody ChatRequest chatRequest) {
        return chatbotService.handleQuery(chatRequest.getQuery(), chatRequest.getEmployeeId());
    }
}
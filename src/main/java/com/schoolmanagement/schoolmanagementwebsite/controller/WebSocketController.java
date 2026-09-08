// package com.schoolmanagement.schoolmanagementwebsite.controller;

// import com.schoolmanagement.schoolmanagementwebsite.dto.WebSocketMessage;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.stereotype.Controller;

// @Controller
// public class WebSocketController {

//     @MessageMapping("/test")
//     @SendTo("/topic/notifications")
//     public WebSocketMessage sendTestMessage(WebSocketMessage message) {

//         return message;
//     }
// }
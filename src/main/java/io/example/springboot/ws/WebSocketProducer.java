package io.example.springboot.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class WebSocketProducer {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(String message, Principal principal) {
        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/server_messages", message);
    }
}

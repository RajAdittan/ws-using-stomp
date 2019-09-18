package io.example.springboot.ws;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;
import java.util.SimpleTimeZone;

@Controller
public class WebsocketController {

    final Logger logger = LoggerFactory.getLogger(WebsocketController.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/message")
    @SendToUser("/queue/reply")
    public String processMessageFromClient(@Payload String message, Principal principal) throws Exception {

        String name = new Gson()
                .fromJson(message, Map.class)
                .get("name").toString();
        return name;
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String processErrors(Throwable exception) {
        return exception.getMessage();
    }
}

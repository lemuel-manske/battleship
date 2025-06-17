package com.battleship;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PlaceShipCommand {

    @MessageMapping("/game")
    @SendTo("/topic/place")
    public String greeting(String message) {
        return message;
    }
}

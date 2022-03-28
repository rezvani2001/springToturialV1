package com.example.demo.Models.messages;

import org.springframework.http.ResponseEntity;

public interface MessagesInterface {
    int getStatusCode();

    String getEnMessage();

    String getFaMessage();
}

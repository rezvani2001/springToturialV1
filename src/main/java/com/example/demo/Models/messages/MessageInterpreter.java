package com.example.demo.Models.messages;

import com.example.demo.Models.responseModels.Response;
import com.example.demo.Models.responseModels.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

public class MessageInterpreter {
    public static ResponseEntity<Object> getDesiredResponse(MessagesInterface message, WebRequest request) {
        return ResponseEntity.status(message.getStatusCode())
                .body(new Response((message.getStatusCode() < 200 || message.getStatusCode() > 300)? Status.FAILED : Status.SUCCESS,
                        Objects.equals(request.getHeader("lang"), "FA") ? message.getFaMessage() : message.getEnMessage()));
    }
}

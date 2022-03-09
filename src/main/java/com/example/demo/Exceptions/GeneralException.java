package com.example.demo.Exceptions;

import com.example.demo.Models.messages.MessagesInterface;
import com.example.demo.Models.responseModels.Response;
import com.example.demo.Models.responseModels.Status;
import org.springframework.http.ResponseEntity;

public class GeneralException extends Exception {
    private MessagesInterface payload;

    public GeneralException(MessagesInterface payload) {
        super();
        this.payload = payload;
    }

    public MessagesInterface getPayload() {
        return payload;
    }

    public void setPayload(MessagesInterface payload) {
        this.payload = payload;
    }

    public ResponseEntity<Object> getEnResponse() {
        return ResponseEntity.status(this.getPayload().getStatusCode())
                .body(new Response(Status.FAILED, this.getPayload().getEnMessage()));
    }


    public ResponseEntity<Object> getFaResponse() {
        return ResponseEntity.status(this.getPayload().getStatusCode())
                .body(new Response(Status.FAILED, this.getPayload().getFaMessage()));
    }
}

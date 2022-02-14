package com.example.demo.extras;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Message {
    private Status status;

    // will be null everywhere for example on http://localhost:8080/api/v1/college
    private final List<Mapping> payload;

    public Message(@Autowired List<Mapping> payload) {
        this.payload = payload;
    }

    public enum Status {
        SUCCESS, FAILED
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<?> getPayload() {
        return payload;
    }

    public void setPayload(Mapping mapping) {
        this.payload.add(mapping);
    }
}

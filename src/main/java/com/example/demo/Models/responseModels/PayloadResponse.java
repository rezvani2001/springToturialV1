package com.example.demo.Models.responseModels;

public class PayloadResponse {
    public Status status = Status.SUCCESS;
    public Object payload;

    public PayloadResponse(Object payload) {
        this.payload = payload;
    }
}

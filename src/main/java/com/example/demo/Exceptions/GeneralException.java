package com.example.demo.Exceptions;

import com.example.demo.Models.messages.MessagesInterface;

public class GeneralException extends Exception {
    private MessagesInterface payload;

    public GeneralException(MessagesInterface payload) {
        super();
        this.payload = payload;
    }

    public MessagesInterface getPayload() {
        return payload;
    }
}

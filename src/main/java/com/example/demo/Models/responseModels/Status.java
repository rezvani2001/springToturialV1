package com.example.demo.Models.responseModels;

public enum Status {
    SUCCESS("success"), FAILED("failed");

    final String name;

    Status(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

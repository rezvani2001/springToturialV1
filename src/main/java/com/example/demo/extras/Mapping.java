package com.example.demo.extras;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
public class Mapping implements Serializable {
    public String name;
    public Object value;

    public Mapping(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}

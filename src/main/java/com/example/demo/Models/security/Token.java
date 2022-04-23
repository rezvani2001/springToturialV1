package com.example.demo.Models.security;

import com.example.demo.Models.User;

import java.util.Date;

public class Token {
    protected String token;
    protected int ttl;
    protected Date date;

    public Token(User user) {
        date = new Date();

        this.token = date.getTime() + "&&" + user.getUsername() + "&&" + 6000 + "&&" + user.getId();
        ttl = 6000;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

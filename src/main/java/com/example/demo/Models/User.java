package com.example.demo.Models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Entity
public class User {
    @Id
    @Column( nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(unique = true, nullable = false, updatable = false)
    @Size(min = 4, max = 15, message = "username length must be between 4 and 15")
    private String username;

    @Column(nullable = false)
    @Size(min = 8, message = "password length must be 8 at lease")
    private String password;

    @Column(unique = true)
    private String token;

    @Column(nullable = true)
    private int ttl;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getAuthenticatedAt() {
        return authenticatedAt;
    }

    public void setAuthenticatedAt(Date authenticatedAt) {
        this.authenticatedAt = authenticatedAt;
    }

    private Date authenticatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}

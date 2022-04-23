package com.example.demo.Models.security;

import com.example.demo.Models.User;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Authority {
    @Id
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String username;

    private String authority;

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

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

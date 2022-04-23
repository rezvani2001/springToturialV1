package com.example.demo.services;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.User;
import com.example.demo.Models.messages.UserMessages;
import com.example.demo.Models.security.CredentialsDTO;
import com.example.demo.Models.security.Token;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    private final UserService userService;
    public static AuthService instance;

    public AuthService(UserService userService) {
        this.userService = userService;
        instance = this;
    }

    public Token authenticate(CredentialsDTO credentials) throws GeneralException {
        User user = userService.getUserByUsername(credentials.username);

        if (user.getPassword().equals(credentials.password)) {
            Token token = new Token(user);
            userService.setToken(user, token);
            return token;
        } else {
            throw new GeneralException(UserMessages.WRONG_PASSWORD);
        }
    }

    public User validateToken(String token) throws GeneralException {
        String[] parts = token.split("&&");

        Date now = new Date();

        Date authenticatedAt = new Date(Long.parseLong(parts[0]) + Integer.parseInt(parts[2]));

        if (authenticatedAt.before(now)) throw new GeneralException(UserMessages.NOT_AUTHENTICATED);

        return userService.getUserByToken(token);
    }
}

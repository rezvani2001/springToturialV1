package com.example.demo.Models.security;

import com.example.demo.Exceptions.GeneralException;
import com.example.demo.Models.User;
import com.example.demo.Models.messages.UserMessages;
import com.example.demo.services.AuthService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class CookieAuthentication extends OncePerRequestFilter {
    AuthService authService;

    public CookieAuthentication(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Optional<Cookie> cookieOP = Arrays.stream(request.getCookies()).filter(cookies -> cookies.getName().equals("auth"))
                .findFirst();
        Cookie cookie = null;

        try {
            if (cookieOP.isPresent()) {
                cookie = cookieOP.get();
                User user = authService.validateToken(cookie.getValue());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}

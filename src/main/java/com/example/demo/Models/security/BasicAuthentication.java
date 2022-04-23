package com.example.demo.Models.security;

import com.example.demo.Models.responseModels.JacksonJson;
import com.example.demo.services.AuthService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BasicAuthentication extends OncePerRequestFilter {
    AuthService authService;

    public BasicAuthentication(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if ("/api/v1/auth/login".equals(request.getServletPath()) && request.getMethod().equals("POST")) {
            StringBuilder body = new StringBuilder();
            String temp = request.getReader().readLine();
            while (temp != null) {
                body.append(temp);
                temp = request.getReader().readLine();
            }

            try {
                CredentialsDTO credentials = (CredentialsDTO) JacksonJson.transfer(body.toString(), new CredentialsDTO());
                Token token = authService.authenticate(credentials);
                Cookie cookie = new Cookie("auth", token.getToken());

                cookie.setMaxAge(token.ttl);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);


                response.addCookie(cookie);
                response.getWriter().println("logged in");
            } catch (Exception e) {
                response.setStatus(401);
                response.getWriter().println("failed to authenticate");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

package com.example.demo.Controllers.v1;

import com.example.demo.Models.security.CredentialsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/auth/")
public class AuthController {

    @RequestMapping(path = "login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody CredentialsDTO credentials) {
        return ResponseEntity.ok("login");
    }
}

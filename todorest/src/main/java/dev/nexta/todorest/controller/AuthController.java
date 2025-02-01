package dev.nexta.todorest.controller;

import dev.nexta.todorest.dtos.AuthDto;
import dev.nexta.todorest.dtos.UserDto;
import dev.nexta.todorest.responses.AuthResponse;
import dev.nexta.todorest.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthDto authDto) {
        return ResponseEntity.ok(authService.signup(authDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthDto authDto) {
        return ResponseEntity.ok(authService.authenticate(authDto));
    }
}

package dev.nexta.todorest.controller;

import dev.nexta.todorest.entity.User;
import dev.nexta.todorest.responses.AuthResponse;
import dev.nexta.todorest.services.AuthService;
import dev.nexta.todorest.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    private final AuthService authService;

    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User registerUserDto) {
        User registeredUser = authService.signup(registerUserDto);

        String jwtToken = jwtService.generateToken(registeredUser);

        AuthResponse registerResponse = AuthResponse.builder()
                .username(registeredUser.getUsername())
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody User loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        AuthResponse loginResponse = AuthResponse.builder()
                .username(authenticatedUser.getUsername())
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }
}

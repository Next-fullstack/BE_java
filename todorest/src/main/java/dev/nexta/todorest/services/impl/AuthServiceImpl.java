package dev.nexta.todorest.services.impl;

import dev.nexta.todorest.dtos.AuthDto;
import dev.nexta.todorest.entity.User;
import dev.nexta.todorest.repository.UserRepository;
import dev.nexta.todorest.responses.AuthResponse;
import dev.nexta.todorest.services.AuthService;
import dev.nexta.todorest.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthServiceImpl(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder,
        JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse signup(AuthDto input) {
        User user = User.builder()
                .username(input.getUsername())
                .password(passwordEncoder.encode(input.getPassword()))
                .build();
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .username(user.getUsername())
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        User user = userRepository.findByUsername(input.getUsername())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .username(user.getUsername())
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }

}

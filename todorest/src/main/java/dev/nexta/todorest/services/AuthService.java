package dev.nexta.todorest.services;

import dev.nexta.todorest.dtos.AuthDto;
import dev.nexta.todorest.responses.AuthResponse;

public interface AuthService {
    
    AuthResponse signup(AuthDto input);

    AuthResponse authenticate(AuthDto input);

}

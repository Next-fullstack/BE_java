package dev.nexta.todorest.services;

import dev.nexta.todorest.dtos.AuthDto;
import dev.nexta.todorest.dtos.UserDto;
import dev.nexta.todorest.responses.AuthResponse;
import dev.nexta.todorest.entity.User;

public interface AuthService {
    
    AuthResponse signup(AuthDto input);

    AuthResponse authenticate(AuthDto input);

    UserDto convertToDto(User user);

    User convertToEntity(UserDto userDto);
}

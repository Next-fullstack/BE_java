package dev.nexta.todorest.services;

import dev.nexta.todorest.entity.User;

public interface AuthService {
    
    public User signup(User input);

    public User authenticate(User input);

}

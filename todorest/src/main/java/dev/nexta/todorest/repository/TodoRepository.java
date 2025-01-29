package dev.nexta.todorest.repository;

import dev.nexta.todorest.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,String>{
    String id(String id);
}
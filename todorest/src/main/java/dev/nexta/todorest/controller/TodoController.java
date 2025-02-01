package dev.nexta.todorest.controller;

import dev.nexta.todorest.dtos.TodoDto;
import dev.nexta.todorest.services.TodoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }
    @GetMapping
    public List<TodoDto> getAllTodos(){
        return todoService.getAllTodos();
    }

    @PostMapping
    public TodoDto createTodo(@RequestBody TodoDto todoDto) {
        if (todoDto.getPriority() != null && !todoDto.getPriority().trim().isEmpty()) {
            return todoService.createTodoWithPriority(todoDto);
        } else {
            return todoService.createTodo(todoDto);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<TodoDto> toggleTodoDone(@PathVariable String id){
        TodoDto updatedTodo = todoService.toggleTodoDone(id);
        return ResponseEntity.ok(updatedTodo);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable String id, @RequestBody TodoDto todoDto){
        TodoDto updatedTodo = todoService.updateTodo(id,todoDto);
        return ResponseEntity.ok(updatedTodo);
    }

    @PutMapping("/{id}/priority")
    public ResponseEntity<TodoDto> updateTodoPriority(@PathVariable String id, @RequestBody Map<String, String> priority) {
        TodoDto updatedTodo = todoService.updateTodoPriority(id, priority.get("priority"));
        return ResponseEntity.ok(updatedTodo);
    }

}

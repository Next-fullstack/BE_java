package dev.nexta.todorest.controller;

import dev.nexta.todorest.entity.Todo;
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
    public List<Todo> getAllTodos(){
        return todoService.getAllTodos();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        if (todo.getPriority() != null && !todo.getPriority().trim().isEmpty()) {
            return todoService.createTodoWithPriority(todo);
        } else {
            return todoService.createTodo(todo);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Todo> toggleTodoDone(@PathVariable String id){
        Todo updatedTodo = todoService.toggleTodoDone(id);
        return ResponseEntity.ok(updatedTodo);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id, @RequestBody Todo todo){
        Todo updatedTodo = todoService.updateTodo(id,todo);
        return ResponseEntity.ok(updatedTodo);
    }

    @PutMapping("/{id}/priority")
    public ResponseEntity<Todo> updateTodoPriority(@PathVariable String id, @RequestBody Map<String, String> priority) {
        Todo updatedTodo = todoService.updateTodoPriority(id, priority.get("priority"));
        return ResponseEntity.ok(updatedTodo);
    }

}
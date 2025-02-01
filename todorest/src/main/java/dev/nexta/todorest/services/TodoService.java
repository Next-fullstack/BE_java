package dev.nexta.todorest.services;

import dev.nexta.todorest.dtos.TodoDto;
import dev.nexta.todorest.entity.Todo;
import dev.nexta.todorest.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoDto> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TodoDto createTodo(TodoDto todoDto) {
        todoDto.setPriority("low");
        return convertToDto(todoRepository.save(convertToEntity(todoDto)));
    }

    public TodoDto createTodoWithPriority(TodoDto todoDto) {
        if (todoDto.getPriority() == null || todoDto.getPriority().trim().isEmpty()) {
            throw new IllegalArgumentException("Priority cannot be null or empty");
        }
        Todo todo = convertToEntity(todoDto);
        return convertToDto(todoRepository.save(todo));
    }

    public void deleteTodo(String id) {
        if (!todoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        todoRepository.deleteById(id);
    }

    public TodoDto toggleTodoDone(String id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not Found"));
        todo.setDone(!todo.isDone());
        return convertToDto(todoRepository.save(todo));
    }

    public TodoDto updateTodo(String id, TodoDto updatedTodoDto) {
        Todo existingTodo = todoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not Found"));
        existingTodo.setTodo(updatedTodoDto.getTodo());
        existingTodo.setDone(updatedTodoDto.isDone());
        return convertToDto(todoRepository.save(existingTodo));
    }

    public TodoDto updateTodoPriority(String id, String priority) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
        existingTodo.setPriority(priority);
        return convertToDto(todoRepository.save(existingTodo));
    }

    private TodoDto convertToDto(Todo todo) {
        return TodoDto.builder()
                .id(todo.getId())
                .todo(todo.getTodo())
                .done(todo.isDone())
                .createdAt(todo.getCreatedAt())
                .priority(todo.getPriority())
                .build();
    }

    private Todo convertToEntity(TodoDto todoDto) {
        return Todo.builder()
                .id(todoDto.getId())
                .todo(todoDto.getTodo())
                .done(todoDto.isDone())
                .priority(todoDto.getPriority())
                .build();
    }
}
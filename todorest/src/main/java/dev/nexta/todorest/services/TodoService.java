package dev.nexta.todorest.services;

import dev.nexta.todorest.entity.Todo;
import dev.nexta.todorest.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo createTodo(Todo todo) {
        if (todo.getTodo() == null || todo.getTodo().trim().isEmpty()) {
            throw new IllegalArgumentException("Todo description cannot be null or empty");
        }
        return todoRepository.save(todo);
    }

    public Todo createTodoWithPriority(Todo todo) {
        if (todo.getPriority() == null || todo.getPriority().trim().isEmpty()) {
            throw new IllegalArgumentException("Priority cannot be null or empty");
        }
        return todoRepository.save(todo);
    }

    public void deleteTodo(String id) {
        if (!todoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        todoRepository.deleteById(id);
    }

    public Todo toggleTodoDone(String id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not Found"));
        todo.setDone(!todo.isDone());
        return todoRepository.save(todo);
    }

    public Todo updateTodo(String id, Todo updatedTodo) {
        Todo existingTodo = todoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not Found"));
        existingTodo.setTodo(updatedTodo.getTodo());
        existingTodo.setDone(updatedTodo.isDone());
        return todoRepository.save(existingTodo);
    }

    public Todo updateTodoPriority(String id, String priority) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
        existingTodo.setPriority(priority);
        return todoRepository.save(existingTodo);
    }

}
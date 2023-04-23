package com.steffenboe.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class TodoRepository {

    private List<Todo> allTodos = new ArrayList<>();

    public List<Todo> findAll() {
        return new ArrayList<>(allTodos);
    }

    public void save(Todo todo) {
        if(allTodos.contains(todo)){
            allTodos.remove(todo);
        }
        allTodos.add(todo);
    }

    public Optional<Todo> findById(String id) {
        return allTodos.stream().filter(todo -> todo.getId().equals(id)).findFirst();
    }

}

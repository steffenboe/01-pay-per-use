package com.steffenboe.todo;

import java.util.Objects;
import java.util.UUID;

public class Todo {

    private String id;
    private String title;
    private boolean completed;

    public Todo() {
        this.id = UUID.randomUUID().toString();
        this.title = "";
        this.completed = false;
    }

    public String getId() {
        return id;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Todo)) {
            return false;
        }
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}

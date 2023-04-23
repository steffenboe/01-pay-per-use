package com.steffenboe.todo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todos")
public class TodoController {
    
    private TodoRepository todoRepository;
    private PaymentGatewayRegistrator paymentGateway;

    public TodoController(TodoRepository todoRepository, PaymentGatewayRegistrator paymentGateway){
        this.todoRepository = todoRepository;
        this.paymentGateway = paymentGateway;
    }
    
    @GetMapping
    public String list(Model model) {
        List<Todo> todos = todoRepository.findAll();
        model.addAttribute("todos", todos);
        model.addAttribute("newTodo", new Todo());
        model.addAttribute("userAddress", paymentGateway.getUserAddress("steffenboe"));
        model.addAttribute("funding", paymentGateway.getCurrentFunding("steffenboe"));
        return "todos/list";
    }
    
    @PostMapping
    public String create(Todo todo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "todos/list";
        }
        
        todoRepository.save(todo);
        try {
            paymentGateway.charge("steffenboe", 0.002);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:/todos";
    }
    
    @GetMapping("/{id}/complete")
    public String complete(@PathVariable("id") String id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setCompleted(true);
            todoRepository.save(todo);
        }
        return "redirect:/todos";
    }
    
}

